package com.matrdata.watchmen.model.gui

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class Favorite(
	var connectedSpaceIds: List<ConnectedSpaceId>? = null,
	var dashboardIds: List<DashboardId>? = null,
	override var lastVisitTime: LocalDateTime?,
	override var userId: UserId?,
	override var tenantId: TenantId?,
	override var createdAt: LocalDateTime?,
	override var createdBy: UserId?,
	override var lastModifiedAt: LocalDateTime?,
	override var lastModifiedBy: UserId?
) : UserBasedTuple, LastVisit
