package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineActionRunnableCommand

sealed class CompiledPipelineAction<T : PipelineActionType, A : PipelineAction<T>>(
	val pipeline: Pipeline,
	val stage: PipelineStage,
	val unit: PipelineUnit,
	val action: A,
	val actionDef: DefJSON
) {
	fun runnable(
		pipeline: CompiledPipeline, stage: CompiledPipelineStage, unit: CompiledPipelineUnit
	): PipelineActionRunnableCommand {
		return PipelineActionRunnableCommand(pipeline, stage, unit, this)
	}
}
