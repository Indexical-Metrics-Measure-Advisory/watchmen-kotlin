package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class UserGroup(
	var userGroupId: UserGroupId? = null,
	var name: String? = null,
	var description: String? = null,
	var userIds: List<UserId>? = null,
	var spaceIds: List<SpaceId>? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock