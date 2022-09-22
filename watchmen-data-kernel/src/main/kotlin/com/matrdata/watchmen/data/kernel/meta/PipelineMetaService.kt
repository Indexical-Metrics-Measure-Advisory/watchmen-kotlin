package com.matrdata.watchmen.data.kernel.meta

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.common.TopicId

class PipelineMetaService(private val principal: Principal) {
	fun findByTopicId(topicId: TopicId): List<Pipeline> {
		TODO("Not yet implemented")
	}
}