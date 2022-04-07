package com.matrdata.model.gui

import com.matrdata.model.base.*
import java.util.*

class LastSnapshot(
	var language: String? = null,
	var lastDashboardId: DashboardId? = null,
	var adminDashboardId: DashboardId? = null,
	var favoritePin: Boolean? = false,
	override val lastVisitTime: Date? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple, LastVisit