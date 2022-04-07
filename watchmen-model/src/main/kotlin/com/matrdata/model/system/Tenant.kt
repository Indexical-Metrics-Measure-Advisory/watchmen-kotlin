package com.matrdata.model.system

import com.matrdata.model.base.*
import java.util.*

class Tenant(
    var tenantId: TenantId? = null,
    var name: StandardTupleName? = null,
    override var createdAt: Date? = null,
    override var createdBy: UserId? = null,
    override var lastModifiedAt: Date? = null,
    override var lastModifiedBy: UserId? = null,
    override var version: Int? = null
) : Auditable, OptimisticLock