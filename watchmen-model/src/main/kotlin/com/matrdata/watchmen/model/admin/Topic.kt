package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class TopicKind(val code: String) {
	SYSTEM("system"),
	BUSINESS("business"),
	SYNONYM("synonym")
}

enum class TopicType(val code: String) {
	RAW("raw"),
	META("meta"),
	DISTINCT("distinct"),
	AGGREGATE("aggregate"),
	TIME("time"),
	RATIO("ratio")
}

data class Topic(
	var topicId: TopicId? = null,
	var name: String? = null,
	var type: TopicType = TopicType.DISTINCT,
	var kind: TopicKind = TopicKind.BUSINESS,
	var dataSourceId: DataSourceId? = null,
	var factors: List<Factor>? = null,
	var description: String? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock
	
