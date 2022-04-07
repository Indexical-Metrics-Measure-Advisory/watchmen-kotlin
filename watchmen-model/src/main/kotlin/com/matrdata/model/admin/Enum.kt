package com.matrdata.model.admin

import com.matrdata.model.base.*
import java.util.*

class EnumItem(
    var itemId: EnumItemId? = null,
    var code: String? = null,
    var label: String? = null,
    var parentCode: String? = null,
    var replaceCode: String? = null,
    var enumId: EnumId? = null,
    var tenantId: TenantId? = null,
) : Storable

class Enum(
    var enumId: EnumId? = null,
    var name: StandardTupleName? = null,
    var description: String? = null,
    var parentEnumId: EnumId? = null,
    var items: List<EnumItem>? = mutableListOf(),
    override var createdAt: Date? = null,
    override var createdBy: UserId? = null,
    override var lastModifiedAt: Date? = null,
    override var lastModifiedBy: UserId? = null,
    override var version: Int? = null,
    override var tenantId: TenantId? = null
) : TenantBasedTuple, OptimisticLock