package com.matrdata.model.gui

import com.matrdata.model.base.*
import java.util.*

class Favorite(
	var connectedSpaceIds: List<ConnectedSpaceId> = mutableListOf(),
	var dashboardIds: List<DashboardId> = mutableListOf(),
	override val lastVisitTime: Date? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple, LastVisit