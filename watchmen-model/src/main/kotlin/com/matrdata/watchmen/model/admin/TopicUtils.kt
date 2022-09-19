package com.matrdata.watchmen.model.admin

object TopicUtils {
	fun findTopicByName(topics: List<Topic>, topicName: String): Topic? {
		return topics.find { it.name == topicName }
	}

	fun findFactorByName(topic: Topic, factorName: String): Factor? {
		return topic.factors?.find { it.name == factorName }
	}
}