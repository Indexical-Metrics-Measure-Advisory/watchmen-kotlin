package com.matrdata.watchmen.data.kernel.meta

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Topic
import com.matrdata.watchmen.model.common.TopicId

class TopicMetaService(private val principal: Principal) {
	fun findById(topicId: TopicId): Topic? {
		TODO("Not yet implemented")
	}
}