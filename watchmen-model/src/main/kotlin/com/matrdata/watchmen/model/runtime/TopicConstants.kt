package com.matrdata.watchmen.model.runtime

enum class TopicDataColumnNames(val code: String) {
	ID("id_"),
	RAW_TOPIC_DATA("data_"),
	AGGREGATE_ASSIST("aggregate_assist_"),
	VERSION("version_"),
	TENANT_ID("tenant_id_"),
	INSERT_TIME("insert_time_"),
	UPDATE_TIME("update_time_")
}
