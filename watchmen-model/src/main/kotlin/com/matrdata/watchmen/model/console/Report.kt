package com.matrdata.watchmen.model.console

import com.matrdata.watchmen.model.chart.Chart
import com.matrdata.watchmen.model.chart.ChartSettings
import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class ReportIndicatorArithmetic(val code: String) {
	NONE("none"),
	COUNT("count"),
	SUMMARY("sum"),
	AVERAGE("avg"),
	MAXIMUM("max"),
	MINIMUM("min")
}

data class ReportIndicator(
	var columnId: SubjectDatasetColumnId? = null,
	var name: String? = null,
	var arithmetic: ReportIndicatorArithmetic? = ReportIndicatorArithmetic.NONE
) : DataModel

data class ReportDimension(
	var columnId: SubjectDatasetColumnId? = null,
	var name: String? = null,
) : DataModel

enum class ReportFunnelType(val code: String) {
	NUMERIC("numeric"),
	DATE("date"),
	YEAR("year"),
	HALF_YEAR("half-year"),
	QUARTER("quarter"),
	MONTH("month"),
	HALF_MONTH("half-month"),
	TEN_DAYS("ten-days"),
	WEEK_OF_MONTH("week-of-month"),
	HALF_WEEK("half-week"),
	DAY_KIND("day-kind"),
	DAY_OF_WEEK("day-of-week"),
	HOUR("hour"),
	HOUR_KIND("hour-kind"),
	AM_PM("am-pm"),
	ENUM("enum")
}

data class ReportFunnel(
	var funnelId: ReportFunnelId? = null,
	var columnId: SubjectDatasetColumnId? = null,
	var type: ReportFunnelType? = null,
	var range: Boolean? = false,
	var enabled: Boolean? = false,
	var values: List<String?>? = null
) : DataModel

data class Report(
	var reportId: ReportId? = null,
	var name: String? = null,
	var subjectId: SubjectId? = null,
	var connectId: ConnectedSpaceId? = null,
	var funnels: List<ReportFunnel>? = null,
	var indicators: List<ReportIndicator>? = null,
	var dimensions: List<ReportDimension>? = null,
	var filters: Joint? = null,
	var description: String? = null,
	var rect: GraphicRect? = null,
	var chart: Chart<out ChartSettings>? = null,
	var simulating: Boolean? = false,
	var simulateData: Dataset? = null,
	// base64
	var simulateThumbnail: String? = null,
	override var lastVisitTime: LocalDateTime? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple, Auditable, LastVisit