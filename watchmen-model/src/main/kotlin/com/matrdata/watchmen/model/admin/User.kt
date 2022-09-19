package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class UserRole(val code: String) {
	CONSOLE("console"),
	ADMIN("admin"),
	SUPER_ADMIN("superadmin")
}


data class User(
	var userId: UserId? = null,
	var name: String? = null,
	var nickName: String? = null,
	var password: String? = null,
	var isActive: Boolean? = true,
	var groupIds: List<UserGroupId>? = null,
	var role: UserRole? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock
