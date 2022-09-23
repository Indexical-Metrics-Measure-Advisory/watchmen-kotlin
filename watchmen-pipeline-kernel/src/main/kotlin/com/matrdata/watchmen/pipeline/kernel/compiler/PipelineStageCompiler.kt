package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineStage
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineStage
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.compiled.PrerequisiteTest
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfNull

class PipelineStageCompiler private constructor(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	// variables assertion from parent level
	private val variables: CompiledVariables
) : Compiler<CompiledPipelineStage> {
	companion object {
		fun of(pipeline: Pipeline, stage: PipelineStage, variables: CompiledVariables): PipelineStageCompiler {
			return PipelineStageCompiler(pipeline, stage, variables)
		}
	}

	override fun compileBy(principal: Principal): CompiledPipelineStage {
		// no variables assertion change in stage level, simply inherit parent's
		return this.variables.inherit().handTo { variablesOnStage ->
			CompiledPipelineStage(
				pipeline = this.pipeline, stage = this.stage,
				variables = variablesOnStage,
				// generate definition json string
				prerequisiteDef = this.stage.toPrerequisiteDefJSON(),
				// compile prerequisite to test function
				prerequisiteTest = this.stage.within(this.pipeline)
					.inherit(variablesOnStage).use(principal)
					.compilePrerequisiteTest(),
				// compile units, inherit variables from stage
				units = this.stage.units.let { units ->
					require(!units.isNullOrEmpty()) { "Unit not exists in pipeline stage[${this.stage}]." }
					return@let units.map { unit ->
						unit.within(this.pipeline, this.stage)
							.inherit(variablesOnStage).use(principal)
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
) : PreparedCompiler<CompiledPipelineStage> {
	fun compilePrerequisiteTest(): PrerequisiteTest {
		return ConditionalCompiler.of(this.stage).compileBy(this.principal)
	}

	override fun compile(): CompiledPipelineStage {
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
