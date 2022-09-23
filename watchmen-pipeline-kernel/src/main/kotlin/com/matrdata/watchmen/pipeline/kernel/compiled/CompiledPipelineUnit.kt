package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineActionType
import com.matrdata.watchmen.model.admin.PipelineStage
import com.matrdata.watchmen.model.admin.PipelineUnit
import com.matrdata.watchmen.utils.ValueUtils

class CompiledPipelineUnit constructor(
	private val pipeline: Pipeline,
	private val stage: PipelineStage,
	private val unit: PipelineUnit,
	/** variables assertion */
	private val variables: CompiledVariables,
	/** json string of prerequisite definition */
	private val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	private val prerequisiteTest: PrerequisiteTest,
	private val actions: List<CompiledPipelineAction<out PipelineActionType>>
) {
	private val loopVariableName: String?
		get() = this.unit.loopVariableName
	private val hasLoop: Boolean
		get() = ValueUtils.isNotBlank(this.loopVariableName)
	// TODO not yet implemented
}
