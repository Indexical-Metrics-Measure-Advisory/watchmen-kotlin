package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.RegularComputedParameter
import com.matrdata.watchmen.model.common.RegularParameterComputeType
import com.matrdata.watchmen.model.common.RegularTopicFactorParameter
import com.matrdata.watchmen.model.common.TopicFactorParameter

object ParameterUtils {
	fun buildTopicFactorParameter(topic: Topic, factorName: String): TopicFactorParameter {
		return RegularTopicFactorParameter(
			topicId = topic.topicId,
			factorId = TopicUtils.findFactorByName(topic, factorName)!!.factorId
		)
	}

	fun buildSingleArgumentComputedParameter(
		topic: Topic, factorName: String, type: RegularParameterComputeType
	): RegularComputedParameter {
		return RegularComputedParameter(
			type = type,
			parameters = listOf(buildTopicFactorParameter(topic, factorName))
		)
	}
}