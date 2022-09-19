package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class EnumItem(
	var itemId: EnumItemId? = null,
	var code: String? = null,
	var label: String? = null,
	var parentCode: String? = null,
	var replaceCode: String? = null,
	var enumId: EnumId? = null,
	var tenantId: TenantId? = null,
) : Storable

data class Enum(
	var enumId: EnumId? = null,
	var name: String? = null,
	var description: String? = null,
	var parentEnumId: EnumId? = null,
	var items: List<EnumItem>? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock

