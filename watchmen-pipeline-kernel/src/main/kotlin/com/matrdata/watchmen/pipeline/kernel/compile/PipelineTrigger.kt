package com.matrdata.watchmen.pipeline.kernel.compile

import com.matrdata.watchmen.model.admin.PipelineTriggerType
import com.matrdata.watchmen.pipeline.kernel.TopicDataId

data class PipelineTrigger(
	val triggerType: PipelineTriggerType,
	val previous: Map<String, Any>?, val current: Map<String, Any>?,
	val dataId: TopicDataId
)