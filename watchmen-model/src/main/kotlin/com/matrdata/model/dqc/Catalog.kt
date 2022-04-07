package com.matrdata.model.dqc

import com.matrdata.model.base.*
import java.util.*

class Catalog(
	var catalogId: CatalogId? = null,
	var name: StandardTupleName? = null,
	var topicIds: List<TopicId>? = null,
	var techOwnerId: UserId? = null,
	var bizOwnerId: UserId? = null,
	var tags: List<String>? = null,
	var description: String? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : TenantBasedTuple, OptimisticLock