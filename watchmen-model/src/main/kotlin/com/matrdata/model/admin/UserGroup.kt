package com.matrdata.model.admin

import com.matrdata.model.base.*
import java.util.*

class UserGroup(
    var userGroupId: UserGroupId? = null,
    var name: StandardTupleName? = null,
    var description: String? = null,
    var userIds: List<UserId>? = null,
    var spaceIds: List<SpaceId>? = null,
    override var createdAt: Date? = null,
    override var createdBy: UserId? = null,
    override var lastModifiedAt: Date? = null,
    override var lastModifiedBy: UserId? = null,
    override var version: Int? = null,
    override var tenantId: TenantId? = null
) : TenantBasedTuple, OptimisticLock