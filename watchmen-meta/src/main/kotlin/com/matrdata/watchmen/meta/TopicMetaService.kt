package com.matrdata.watchmen.meta

import com.matrdata.watchmen.model.admin.Topic
import com.matrdata.watchmen.model.common.TopicId

class TopicMetaService {
	fun findById(topicId: TopicId): Topic {
		TODO("Not yet implemented")
	}
}

fun askTopicMetaService(): TopicMetaService {
	return TopicMetaService()
}