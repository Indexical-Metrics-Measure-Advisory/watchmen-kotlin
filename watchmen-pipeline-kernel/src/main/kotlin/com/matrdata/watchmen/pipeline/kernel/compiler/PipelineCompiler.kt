package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.PrerequisiteTest
import com.matrdata.watchmen.utils.handTo

class PipelineCompiler private constructor(private val pipeline: Pipeline) : Compiler<CompiledPipeline> {
	companion object {
		fun of(pipeline: Pipeline): PipelineCompiler {
			return PipelineCompiler(pipeline)
		}
	}

	private fun compilePrerequisiteTest(variables: CompiledVariables, principal: Principal): PrerequisiteTest {
		// TODO use variables on prerequisite compiling
		return ConditionalCompiler.of(this.pipeline).compileBy(principal)
	}

	override fun compileBy(principal: Principal): CompiledPipeline {
		// create a compiled variables to trace the variable changes
		// this variables will be used through the entire compiling
		// variables must be copied to create a snapshot when enter each stage/unit/action
		// also variables is copied when passed to pipeline prerequisite
		return CompiledVariables().handTo { variables ->
			CompiledPipeline(
				pipeline = this.pipeline,
				// generate definition json string
				prerequisiteDef = this.pipeline.toPrerequisiteDefJSON(),
				// compile prerequisite to test function
				prerequisiteTest = this.compilePrerequisiteTest(variables.copy(), principal),
				stages = this.pipeline.stages.let { stages ->
					require(!stages.isNullOrEmpty()) { "Stage not exists in pipeline[${this.pipeline}]." }
					stages.map { stage ->
						stage.within(this.pipeline)
							.inherit(variables).use(principal)
							.compile()
					}
				}
			)
		}
	}
}

class PreparedPipelineCompiler(
	private val pipeline: Pipeline, private val principal: Principal
) : PreparedCompiler<CompiledPipeline> {
	override fun compile(): CompiledPipeline {
		return PipelineCompiler.of(this.pipeline).compileBy(this.principal)
	}
}

fun Pipeline.use(principal: Principal): PreparedPipelineCompiler {
	return PreparedPipelineCompiler(pipeline = this, principal = principal)
}
