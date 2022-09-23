package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineStage
import com.matrdata.watchmen.model.admin.PipelineUnit
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineUnit
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.compiled.PrerequisiteTest
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfNull

class PipelineUnitCompiler private constructor(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit,
	private val variables: CompiledVariables
) : Compiler<CompiledPipelineUnit> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit,
			variables: CompiledVariables
		): PipelineUnitCompiler {
			return PipelineUnitCompiler(pipeline, stage, unit, variables)
		}
	}

	override fun compileBy(principal: Principal): CompiledPipelineUnit {
		// no variables assertion change in unit level, simply inherit parent's
		return this.variables.inherit().handTo { variablesOnUnit ->
			CompiledPipelineUnit(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				variables = variablesOnUnit,
				// generate definition json string
				prerequisiteDef = this.unit.toPrerequisiteDefJSON(),
				// compile prerequisite to test function
				prerequisiteTest = this.unit.within(this.pipeline, this.stage)
					.inherit(variablesOnUnit).use(principal)
					.compilePrerequisiteTest(),
				actions = this.unit.`do`.let { actions ->
					require(!actions.isNullOrEmpty()) { "Action not exists in pipeline unit[${this.unit}]." }
					return@let actions.map { action ->
						action.within(this.pipeline, this.stage, this.unit)
							.inherit(variablesOnUnit).use(principal)
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
) : PreparedCompiler<CompiledPipelineUnit> {
	fun compilePrerequisiteTest(): PrerequisiteTest {
		return ConditionalCompiler.of(this.unit).compileBy(this.principal)
	}

	override fun compile(): CompiledPipelineUnit {
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
