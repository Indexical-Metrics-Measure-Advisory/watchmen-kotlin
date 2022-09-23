package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.*

sealed class CompiledPipelineAction<T : PipelineActionType>(
	private val pipeline: Pipeline,
	private val stage: PipelineStage,
	private val unit: PipelineUnit,
	private val action: PipelineAction<T>,
	/** json string of action definition */
	private val actionDef: DefJSON
)
