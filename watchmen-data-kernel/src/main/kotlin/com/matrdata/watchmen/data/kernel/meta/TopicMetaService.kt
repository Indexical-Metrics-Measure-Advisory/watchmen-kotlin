package com.matrdata.watchmen.data.kernel.meta

import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.Topic
import com.matrdata.watchmen.model.common.TopicId
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfBlank
import com.matrdata.watchmen.utils.useIfNull

class TopicMetaService {
	fun findById(topicId: TopicId): Topic? {
		TODO("Not yet implemented")
	}

	private fun askTopicSchemaFromCache(topicId: TopicId): TopicSchema? {
		TODO("Not yet implemented")
	}

	private fun putTopicSchemaIntoCache(schema: TopicSchema) {
		TODO("Not yet implemented")
	}

	fun findSchemaById(topicId: TopicId): TopicSchema? {
		return topicId
			.throwIfBlank { "Topic id cannot be null or blank." }
			.handTo { id -> askTopicSchemaFromCache(id) }
			.useIfNull {
				this.findById(topicId)
					?.handTo { topic -> TopicSchema(topic) }
					?.also { schema -> putTopicSchemaIntoCache(schema) }
			}
	}
}