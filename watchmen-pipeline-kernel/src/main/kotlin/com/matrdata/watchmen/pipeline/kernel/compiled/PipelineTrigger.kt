package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.PipelineTriggerType
import com.matrdata.watchmen.model.common.TopicDataId

data class PipelineTrigger(
	val triggerType: PipelineTriggerType,
	val previous: Map<String, Any>?, val current: Map<String, Any>?,
	val dataId: TopicDataId
)