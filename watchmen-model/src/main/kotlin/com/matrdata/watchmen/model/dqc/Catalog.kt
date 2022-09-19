package com.matrdata.watchmen.model.dqc

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class Catalog(
	var catalogId: CatalogId? = null,
	var name: String? = null,
	var topicIds: List<TopicId>? = null,
	var techOwnerId: UserId? = null,
	var bizOwnerId: UserId? = null,
	var tags: List<String>? = null,
	var description: String? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock

data class CatalogCriteria(
	var name: String? = null,
	var topicId: TopicId? = null,
	var techOwnerId: UserId? = null,
	var bizOwnerId: UserId? = null
)