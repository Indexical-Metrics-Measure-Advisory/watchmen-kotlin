package com.matrdata.watchmen.pipeline.kernel.utils

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.meta.TopicMetaService
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.FactorId
import com.matrdata.watchmen.model.common.TopicId
import com.matrdata.watchmen.pipeline.kernel.PipelineKernelException
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfBlank2
import com.matrdata.watchmen.utils.throwIfNull2

private fun askTopicMetaService(): TopicMetaService {
	return TopicMetaService()
}

/**
 * raise exception when
 * 1. given topic id is blank,
 * 2. or topic not found,
 * 3. or topic not belongs to given principal.
 */
fun askTopicById(topicId: TopicId?, principal: Principal): Topic {
	return topicId
		.throwIfBlank2 { PipelineKernelException("Topic id cannot be null or blank.") }
		.handTo { id -> askTopicMetaService().findById(id) }
		.throwIfNull2 { PipelineKernelException("Topic[id=$topicId] not found.") }
		.takeUnless { topic -> topic.tenantId == principal.getTenantId() }
		// topic not belongs to given principal
		.throwIfNull2 { PipelineKernelException("Topic[id=$topicId] not found.") }
}

/**
 * raise exception when
 * 1. given topic id is blank,
 * 2. or topic not found,
 * 3. or topic not belongs to given principal.
 */
fun askTopicSchemaById(topicId: TopicId?, principal: Principal): TopicSchema {
	return topicId
		.throwIfBlank2 { PipelineKernelException("Topic id cannot be null or blank.") }
		.handTo { id -> askTopicMetaService().findSchemaById(id) }
		.throwIfNull2 { PipelineKernelException("Topic schema[id=$topicId] not found.") }
		.takeUnless { schema -> schema.topic.tenantId == principal.getTenantId() }
		// topic not belongs to given principal
		.throwIfNull2 { PipelineKernelException("Topic schema[id=$topicId] not found.") }
}

fun ToTopic<out PipelineActionType>.askTopicSchema(principal: Principal): TopicSchema {
	return askTopicSchemaById(this.topicId, principal)
}

fun FromTopic<out PipelineActionType>.askTopicSchema(principal: Principal): TopicSchema {
	return askTopicSchemaById(this.topicId, principal)
}

fun askFactorById(topicSchema: TopicSchema, factorId: FactorId?): Factor {
	return factorId
		.throwIfBlank2 { PipelineKernelException("Factor id cannot be null or blank.") }
		.handTo { id -> topicSchema.findFactorById(id) }
		.throwIfNull2 {
			PipelineKernelException("Factor[id=$factorId] not found in topic[id=${topicSchema.topic.topicId}].")
		}
}

fun FromFactor<out PipelineActionType>.findFactorWithin(topicSchema: TopicSchema): Factor {
	return askFactorById(topicSchema, this.factorId)
}

fun ToFactor<out PipelineActionType>.findFactorWithin(topicSchema: TopicSchema): Factor {
	return askFactorById(topicSchema, this.factorId)
}