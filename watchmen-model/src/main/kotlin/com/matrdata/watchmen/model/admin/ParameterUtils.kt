package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.SingleArgumentComputeType
import com.matrdata.watchmen.model.common.StandardComputedNonConditionalParameter
import com.matrdata.watchmen.model.common.StandardTopicFactorParameter

object ParameterUtils {
	fun buildStandardFactorParameter(topic: Topic, factorName: String): StandardTopicFactorParameter {
		return StandardTopicFactorParameter(
			topicId = topic.topicId,
			factorId = TopicUtils.findFactorByName(topic, factorName)!!.factorId
		)
	}

	fun buildSingleArgumentComputedParameter(
		topic: Topic, factorName: String, type: SingleArgumentComputeType
	): StandardComputedNonConditionalParameter {
		return StandardComputedNonConditionalParameter(
			type = type,
			parameters = listOf(buildStandardFactorParameter(topic, factorName))
		)
	}
}