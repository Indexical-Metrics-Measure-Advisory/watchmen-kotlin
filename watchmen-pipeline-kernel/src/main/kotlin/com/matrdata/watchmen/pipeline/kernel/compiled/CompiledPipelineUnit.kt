package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineUnitRunnableCommand
import com.matrdata.watchmen.utils.ValueUtils

class CompiledPipelineUnit constructor(
	val pipeline: Pipeline,
	val stage: PipelineStage,
	val unit: PipelineUnit,
	/** compiled variables */
	val variables: CompiledVariables,
	/** json string of prerequisite definition */
	val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	val prerequisiteTest: PrerequisiteTest,
	val actions: List<CompiledPipelineAction<out PipelineActionType, out PipelineAction<out PipelineActionType>>>
) {
	val loopVariableName: String?
		get() = this.unit.loopVariableName
	val hasLoop: Boolean
		get() = ValueUtils.isNotBlank(this.loopVariableName)

	fun runnable(pipeline: CompiledPipeline, stage: CompiledPipelineStage): PipelineUnitRunnableCommand {
		return PipelineUnitRunnableCommand(pipeline, stage, this)
	}
}
