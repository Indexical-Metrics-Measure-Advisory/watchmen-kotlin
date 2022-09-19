package com.matrdata.watchmen.model.console

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class ConnectedSpace(
	var connectId: ConnectedSpaceId? = null,
	var spaceId: SpaceId? = null,
	var name: String? = null,
	var isTemplate: Boolean? = false,
	override var lastVisitTime: LocalDateTime? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple, Auditable, LastVisit
