package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineStage
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineStageRunnableCommand

class CompiledPipelineStage constructor(
	val pipeline: Pipeline,
	val stage: PipelineStage,
	/** compiled variables */
	val variables: CompiledVariables,
	/** json string of prerequisite definition */
	val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	val prerequisiteTest: PrerequisiteTest,
	val units: List<CompiledPipelineUnit>
) {
	fun runnable(pipeline: CompiledPipeline): PipelineStageRunnableCommand {
		return PipelineStageRunnableCommand(pipeline, this)
	}
}
