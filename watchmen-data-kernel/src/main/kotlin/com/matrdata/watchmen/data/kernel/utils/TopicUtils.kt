package com.matrdata.watchmen.data.kernel.utils

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.DataKernelException
import com.matrdata.watchmen.meta.askTopicMetaService
import com.matrdata.watchmen.model.admin.Topic
import com.matrdata.watchmen.model.common.TopicId
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfBlank2
import com.matrdata.watchmen.utils.throwIfNull2

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
