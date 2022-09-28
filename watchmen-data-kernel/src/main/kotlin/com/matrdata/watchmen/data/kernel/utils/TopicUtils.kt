package com.matrdata.watchmen.data.kernel.utils

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.DataKernelException
import com.matrdata.watchmen.data.kernel.meta.TopicMetaService
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.Factor
import com.matrdata.watchmen.model.admin.Topic
import com.matrdata.watchmen.model.common.FactorId
import com.matrdata.watchmen.model.common.TopicId
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
fun askTopicById(topicId: TopicId?, principal: Principal, location: () -> String): Topic {
	return topicId
		.throwIfBlank2 { DataKernelException("Topic id of ${location()} cannot be null or blank.") }
		.handTo { id -> askTopicMetaService().findById(id) }
		.throwIfNull2 { DataKernelException("Topic[id=$topicId] of ${location()} not found.") }
		.takeUnless { topic -> topic.tenantId == principal.getTenantId() }
		// topic not belongs to given principal
		.throwIfNull2 { DataKernelException("Topic[id=$topicId] of ${location()} not found.") }
}

/**
 * raise exception when
 * 1. given topic id is null or blank,
 * 2. or topic not found,
 * 3. or topic not belongs to given principal.
 */
fun askTopicSchemaById(topicId: TopicId?, principal: Principal, location: () -> String): TopicSchema {
	return topicId
		.throwIfBlank2 { DataKernelException("Topic id of ${location()} cannot be null or blank.") }
		.handTo { id -> askTopicMetaService().findSchemaById(id) }
		.throwIfNull2 { DataKernelException("Topic schema[id=$topicId] of ${location()} not found.") }
		.takeUnless { schema -> schema.topic.tenantId == principal.getTenantId() }
		// topic not belongs to given principal
		.throwIfNull2 { DataKernelException("Topic schema[id=$topicId] of ${location()} not found.") }
}

/**
 * raise exception when
 * 1. given factor id is null or blank,
 * 2. factor does not exist in given topic.
 */
fun askFactorById(topicSchema: TopicSchema, factorId: FactorId?, location: () -> String): Factor {
	return factorId
		.throwIfBlank2 { DataKernelException("Factor id of ${location()} cannot be null or blank.") }
		.handTo { id -> topicSchema.findFactorById(id) }
		.throwIfNull2 {
			DataKernelException("Factor[id=$factorId] of ${location()} not found in topic[id=${topicSchema.topic.topicId}].")
		}
}
