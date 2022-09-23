package com.matrdata.watchmen.model.gui

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class Favorite(
	var connectedSpaceIds: List<ConnectedSpaceId>? = null,
	var dashboardIds: List<DashboardId>? = null,
	override var lastVisitTime: LocalDateTime? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple, LastVisit
