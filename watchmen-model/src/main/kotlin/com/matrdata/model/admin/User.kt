package com.matrdata.model.admin

import com.matrdata.model.base.*
import java.util.*

enum class UserRole(val code: String) {
    CONSOLE("console"),
    ADMIN("admin"),
    SUPER_ADMIN("superadmin")
}

class User(
    var userId: UserId? = null,
    var name: StandardTupleName? = null,
    var nickName: String? = null,
    var password: String? = null,
    var isActive: Boolean? = true,
    var groupIds: List<UserGroupId>? = null,
    var role: UserRole? = null,
    override var createdAt: Date? = null,
    override var createdBy: UserId? = null,
    override var lastModifiedAt: Date? = null,
    override var lastModifiedBy: UserId? = null,
    override var version: Int? = null,
    override var tenantId: TenantId? = null
) : TenantBasedTuple, OptimisticLock