package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineStage
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledStage
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.compiled.PrerequisiteTest
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfNull

class PipelineStageCompiler private constructor(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	// compiled variables from parent level
	private val variables: CompiledVariables
) : Compiler<CompiledStage> {
	companion object {
		fun of(pipeline: Pipeline, stage: PipelineStage, variables: CompiledVariables): PipelineStageCompiler {
			return PipelineStageCompiler(pipeline, stage, variables)
		}
	}

	private fun compilePrerequisiteTest(variables: CompiledVariables, principal: Principal): PrerequisiteTest {
		// TODO use variables on prerequisite compiling
		return ConditionalCompiler.of(this.stage).compileBy(principal)
	}

	override fun compileBy(principal: Principal): CompiledStage {
		// copy compiled variables for myself
		return this.variables.copy().handTo { variablesOnStage ->
			CompiledStage(
				pipeline = this.pipeline, stage = this.stage,
				variables = variablesOnStage,
				// generate definition json string
				prerequisiteDef = this.stage.toPrerequisiteDefJSON(),
				// compile prerequisite to test function
				prerequisiteTest = this.compilePrerequisiteTest(variablesOnStage, principal),
				// compile units, inherit variables from stage
				units = this.stage.units.let { units ->
					require(!units.isNullOrEmpty()) { "Unit not exists in pipeline stage[${this.stage}]." }
					units.map { unit ->
						unit.within(this.pipeline, this.stage)
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

class PreparedPipelineStageCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val variables: CompiledVariables,
	private val principal: Principal
) : PreparedCompiler<CompiledStage> {
	override fun compile(): CompiledStage {
		return PipelineStageCompiler.of(this.pipeline, this.stage, this.variables).compileBy(this.principal)
	}
}

class PrePreparedPipelineStageCompiler(private val pipeline: Pipeline, private val stage: PipelineStage) {
	private var variables: CompiledVariables? = null
	fun inherit(variables: CompiledVariables): PrePreparedPipelineStageCompiler {
		this.variables = variables
		return this
	}

	fun use(principal: Principal): PreparedPipelineStageCompiler {
		return PreparedPipelineStageCompiler(
			pipeline = this.pipeline, stage = this.stage,
			variables = this.variables.throwIfNull { "Compiled variables cannot be null." },
			principal = principal
		)
	}
}

fun PipelineStage.within(pipeline: Pipeline): PrePreparedPipelineStageCompiler {
	return PrePreparedPipelineStageCompiler(pipeline, this)
}
