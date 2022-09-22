package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class SpaceFilter(
	var topicId: TopicId? = null,
	var enabled: Boolean? = false,
	var joint: Joint? = null
) : DataModel

data class Space(
	var spaceId: SpaceId? = null,
	var name: String? = null,
	var description: String? = null,
	var topicIds: List<TopicId>? = null,
	var groupIds: List<UserGroupId>? = null,
	var filters: List<SpaceFilter>? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock
	
