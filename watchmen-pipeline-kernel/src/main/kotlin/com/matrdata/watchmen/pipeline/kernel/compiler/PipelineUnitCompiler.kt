package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineStage
import com.matrdata.watchmen.model.admin.PipelineUnit
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledUnit
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.compiled.PrerequisiteTest
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfNull

class PipelineUnitCompiler private constructor(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit,
	private val variables: CompiledVariables
) : Compiler<CompiledUnit> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit,
			variables: CompiledVariables
		): PipelineUnitCompiler {
			return PipelineUnitCompiler(pipeline, stage, unit, variables)
		}
	}

	private fun compilePrerequisiteTest(variables: CompiledVariables, principal: Principal): PrerequisiteTest {
		// TODO use variables on prerequisite test
		return ConditionalCompiler.of(this.unit).compileBy(principal)
	}

	override fun compileBy(principal: Principal): CompiledUnit {
		// copy compiled variables for myself
		return this.variables.copy().handTo { variablesOnUnit ->
			CompiledUnit(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				variables = variablesOnUnit,
				// generate definition json string
				prerequisiteDef = this.unit.toPrerequisiteDefJSON(),
				// compile prerequisite to test function
				prerequisiteTest = this.compilePrerequisiteTest(variablesOnUnit, principal),
				actions = this.unit.`do`.let { actions ->
					require(!actions.isNullOrEmpty()) { "Action not exists in pipeline unit[${this.unit}]." }
					actions.map { action ->
						action.within(this.pipeline, this.stage, this.unit)
							// always use the root variables
							.inherit(this.variables)
							.use(principal)
							.compile()
					}
				}
			)
		}
	}
}

class PreparedPipelineUnitCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit,
	private val variables: CompiledVariables,
	private val principal: Principal
) : PreparedCompiler<CompiledUnit> {
	override fun compile(): CompiledUnit {
		return PipelineUnitCompiler.of(this.pipeline, this.stage, this.unit, this.variables).compileBy(this.principal)
	}
}

class PrePreparedPipelineUnitCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit
) {
	private var variables: CompiledVariables? = null
	fun inherit(variables: CompiledVariables): PrePreparedPipelineUnitCompiler {
		this.variables = variables
		return this
	}

	fun use(principal: Principal): PreparedPipelineUnitCompiler {
		return PreparedPipelineUnitCompiler(
			pipeline = this.pipeline, stage = this.stage, unit = this.unit,
			variables = this.variables.throwIfNull { "Compiled variables cannot be null." },
			principal = principal
		)
	}
}

fun PipelineUnit.within(pipeline: Pipeline, stage: PipelineStage): PrePreparedPipelineUnitCompiler {
	return PrePreparedPipelineUnitCompiler(pipeline, stage, this)
}
