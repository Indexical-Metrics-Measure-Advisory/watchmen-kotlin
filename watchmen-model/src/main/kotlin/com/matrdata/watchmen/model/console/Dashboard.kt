package com.matrdata.watchmen.model.console

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class DashboardReport(
	var reportId: ReportId? = null,
	var funnels: List<ReportFunnel>? = null,
	var rect: GraphicRect? = null
) : DataModel

data class DashboardParagraph(
	var content: String? = null,
	var rect: GraphicRect? = null
) : DataModel

data class Dashboard(
	var dashboardId: DashboardId? = null,
	var name: String? = null,
	var reports: List<DashboardReport>? = null,
	var paragraphs: List<DashboardParagraph>? = null,
	var autoRefreshInterval: Int? = null,
	override var lastVisitTime: LocalDateTime? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple, Auditable, LastVisit