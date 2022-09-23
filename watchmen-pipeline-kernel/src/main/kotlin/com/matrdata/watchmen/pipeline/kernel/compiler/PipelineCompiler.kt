package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.compiled.PrerequisiteTest

class PipelineCompiler private constructor(private val pipeline: Pipeline) : Compiler<CompiledPipeline> {
	companion object {
		fun of(pipeline: Pipeline): PipelineCompiler {
			return PipelineCompiler(pipeline)
		}
	}

	override fun compileBy(principal: Principal): CompiledPipeline {
		return CompiledPipeline(
			pipeline = this.pipeline,
			// generate definition json string
			prerequisiteDef = this.pipeline.toPrerequisiteDefJSON(),
			// compile prerequisite to test function
			prerequisiteTest = this.pipeline.use(principal).compilePrerequisiteTest(),
			stages = this.pipeline.stages.let { stages ->
				require(!stages.isNullOrEmpty()) { "Stage not exists in pipeline[${this.pipeline}]." }
				// variables assertion is used by children
				val variables = CompiledVariables()
				return@let stages.map { stage ->
					stage.within(this.pipeline)
						.inherit(variables).use(principal)
						.compile()
				}
			}
		)
	}
}

class PreparedPipelineCompiler(
	private val pipeline: Pipeline, private val principal: Principal
) : PreparedCompiler<CompiledPipeline> {
	fun compilePrerequisiteTest(): PrerequisiteTest {
		return ConditionalCompiler.of(this.pipeline).compileBy(this.principal)
	}

	override fun compile(): CompiledPipeline {
		return PipelineCompiler.of(this.pipeline).compileBy(this.principal)
	}
}

fun Pipeline.use(principal: Principal): PreparedPipelineCompiler {
	return PreparedPipelineCompiler(pipeline = this, principal = principal)
}
