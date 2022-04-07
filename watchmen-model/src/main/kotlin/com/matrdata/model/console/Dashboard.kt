package com.matrdata.model.console

import com.matrdata.model.base.*
import com.matrdata.model.common.GraphicRect
import java.util.*

class DashboardReport(
	var reportId: ReportId? = null,
	var funnels: List<ReportFunnel>? = null,
	var rect: GraphicRect? = null
)

class DashboardParagraph(
	content: String? = null,
	rect: GraphicRect? = null
)

class Dashboard(
	var dashboardId: DashboardId? = null,
	var name: StandardTupleName? = null,
	var reports: List<DashboardReport>? = null,
	var paragraphs: List<DashboardParagraph>? = null,
	var autoRefreshInterval: Int? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override val lastVisitTime: Date? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null,
) : UserBasedTuple, Auditable, LastVisit