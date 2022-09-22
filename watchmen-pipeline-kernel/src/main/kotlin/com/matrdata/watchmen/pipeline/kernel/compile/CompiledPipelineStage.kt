package com.matrdata.watchmen.pipeline.kernel.compile

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineStage
import com.matrdata.watchmen.pipeline.kernel.compile.conditional.PrerequisiteTest
import com.matrdata.watchmen.pipeline.kernel.compile.conditional.use

class PipelineStageCompiler private constructor(
	private val pipeline: Pipeline, private val stage: PipelineStage
) : Compiler<CompiledPipelineStage> {
	companion object {
		fun of(pipeline: Pipeline, stage: PipelineStage): PipelineStageCompiler {
			return PipelineStageCompiler(pipeline, stage)
		}
	}

	override fun compileBy(principal: Principal): CompiledPipelineStage {
		return CompiledPipelineStage(
			pipeline = this.pipeline,
			stage = this.stage,
			// generate definition json string
			prerequisiteDef = stage.toPrerequisiteDefJSON(),
			// compile prerequisite to test function
			prerequisiteTest = stage.use(principal).compile(),
			units = with(stage.units) {
				require(!this.isNullOrEmpty()) { "Unit not exists in pipeline stage[$stage]." }
				this.map { it.within(pipeline, stage).use(principal).compile() }
			}
		)
	}
}

class CompiledPipelineStage constructor(
	private val pipeline: Pipeline,
	private val stage: PipelineStage,
	/** json string of prerequisite definition */
	private val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	private val prerequisiteTest: PrerequisiteTest,
	private val units: List<CompiledPipelineUnit>
) {
	// TODO not yet implemented
}

class PreparedPipelineStageCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val principal: Principal
) : PreparedCompiler<CompiledPipelineStage> {
	override fun compile(): CompiledPipelineStage {
		return PipelineStageCompiler.of(this.pipeline, this.stage).compileBy(this.principal)
	}
}

class PrePreparedPipelineStageCompiler(private val pipeline: Pipeline, private val stage: PipelineStage) {
	fun use(principal: Principal): PreparedPipelineStageCompiler {
		return PreparedPipelineStageCompiler(pipeline = this.pipeline, stage = this.stage, principal = principal)
	}
}

fun PipelineStage.within(pipeline: Pipeline): PrePreparedPipelineStageCompiler {
	return PrePreparedPipelineStageCompiler(pipeline, this)
}
