package com.matrdata.watchmen.pipeline.kernel

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.common.PipelineTriggerTraceId
import com.matrdata.watchmen.model.common.TopicDataId

class PipelineTask(
	private val pipeline: Pipeline,
	private val previousData: Map<String, Any>?, private val currentData: Map<String, Any>?,
	private val principal: Principal,
	private val traceId: PipelineTriggerTraceId, private val dataId: TopicDataId
) {
	companion object {
		const val UNKNOWN_SYNONYM_DATA_ID: TopicDataId = -1
	}

	fun run(handleMonitorLog: PipelineMonitorLogHandle): List<PipelineTask> {
		TODO("Not yet implemented")
	}
}
