package com.matrdata.watchmen.pipeline.kernel.utils

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.data.kernel.utils.askFactorById
import com.matrdata.watchmen.data.kernel.utils.askTopicSchemaById
import com.matrdata.watchmen.model.admin.*

fun ToTopic<out PipelineActionType>.askTopicSchema(principal: Principal): TopicSchema {
	return askTopicSchemaById(this.topicId, principal) { "action[id=${this.actionId}]" }
}

fun FromTopic<out PipelineActionType>.askTopicSchema(principal: Principal): TopicSchema {
	return askTopicSchemaById(this.topicId, principal) { "action[id=${this.actionId}]" }
}

fun FromFactor<out PipelineActionType>.findFactorWithin(topicSchema: TopicSchema): Factor {
	return askFactorById(topicSchema, this.factorId) { "action[id=${this.actionId}]" }
}

fun ToFactor<out PipelineActionType>.findFactorWithin(topicSchema: TopicSchema): Factor {
	return askFactorById(topicSchema, this.factorId) { "action[id=${this.actionId}]" }
}