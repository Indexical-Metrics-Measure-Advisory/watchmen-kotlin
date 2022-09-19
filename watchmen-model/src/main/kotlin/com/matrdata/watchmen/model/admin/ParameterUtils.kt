package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.ComputedParameter
import com.matrdata.watchmen.model.common.ParameterComputeType
import com.matrdata.watchmen.model.common.TopicFactorParameter

object ParameterUtils {
	fun buildTopicFactorParameter(topic: Topic, factorName: String): TopicFactorParameter {
		return TopicFactorParameter(
			topicId = topic.topicId,
			factorId = TopicUtils.findFactorByName(topic, factorName)!!.factorId
		)
	}

	fun buildSingleArgumentComputedParameter(
		topic: Topic, factorName: String, type: ParameterComputeType
	): ComputedParameter {
		return ComputedParameter(
			type = type,
			parameters = listOf(buildTopicFactorParameter(topic, factorName))
		)
	}
}