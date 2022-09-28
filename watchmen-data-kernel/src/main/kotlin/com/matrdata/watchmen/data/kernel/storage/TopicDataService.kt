package com.matrdata.watchmen.data.kernel.storage

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.storage.TopicDataStorage

/**
 * TODO topic data service
 */
abstract class TopicDataService(
	val topicSchema: TopicSchema,
	val storage: TopicDataStorage,
	val principal: Principal
) {
	fun findById(id: Long): Map<String, Any?> {
		TODO("Not yet implemented")
	}
}