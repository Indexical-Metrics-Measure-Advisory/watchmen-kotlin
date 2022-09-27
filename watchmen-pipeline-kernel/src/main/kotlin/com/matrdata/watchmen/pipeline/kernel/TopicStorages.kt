package com.matrdata.watchmen.pipeline.kernel

import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.common.DataSourceId
import com.matrdata.watchmen.utils.throwIfBlank
import java.security.Principal

interface TopicDataStorage

class TopicStorages(private val principal: Principal) {
	private val storages: MutableMap<DataSourceId, TopicDataStorage> = mutableMapOf()

	private fun createStorage(): TopicDataStorage {
		TODO("Not yet implemented")
	}

	fun askStorage(schema: TopicSchema): TopicDataStorage {
		val topic = schema.topic
		val dataSourceId = topic.dataSourceId.throwIfBlank {
			"Data source is not defined for topic[id=${topic.topicId}, name=${topic.name}]."
		}
		return this.storages[dataSourceId] ?: createStorage().also { this.storages[dataSourceId] = it }
	}
}