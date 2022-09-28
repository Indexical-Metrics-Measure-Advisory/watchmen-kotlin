package com.matrdata.watchmen.storage

import com.matrdata.watchmen.model.admin.Topic

interface TopicStructureStorage : Storage {
	/**
	 * create topic
	 */
	fun create(topic: Topic)

	/**
	 * update topic structure
	 */
	fun update(topic: Topic, original: Topic)

	/**
	 * drop topic
	 */
	fun drop(topic: Topic)

	/**
	 * truncate topic data
	 */
	fun truncate(topic: Topic)
}