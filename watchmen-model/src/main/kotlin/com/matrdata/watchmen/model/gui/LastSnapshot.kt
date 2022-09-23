package com.matrdata.watchmen.model.gui

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class LastSnapshot(
	var language: String? = null,
	var lastDashboardId: DashboardId? = null,
	var adminDashboardId: DashboardId? = null,
	var favoritePin: Boolean? = false,
	override var lastVisitTime: LocalDateTime? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple, LastVisit
