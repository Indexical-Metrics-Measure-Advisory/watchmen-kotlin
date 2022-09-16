package com.matrdata.watchmen.model.system

import com.matrdata.watchmen.model.common.OptimisticLock
import com.matrdata.watchmen.model.common.TenantId
import com.matrdata.watchmen.model.common.Tuple
import com.matrdata.watchmen.model.common.UserId
import java.time.LocalDateTime

data class Tenant(
	var tenantId: TenantId? = null,
	var name: String? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : Tuple, OptimisticLock
