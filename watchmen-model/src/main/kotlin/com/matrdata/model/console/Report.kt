package com.matrdata.model.console

import com.matrdata.model.base.*
import com.matrdata.model.common.DataResultSet
import com.matrdata.model.common.GraphicRect
import com.matrdata.model.common.ParameterJoint
import java.util.*

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

class ReportFunnel(
	var funnelId: ReportFunnelId? = null,
	var columnId: SubjectDatasetColumnId? = null,
	var type: ReportFunnelType? = null,
	var range: Boolean? = false,
	var enabled: Boolean? = false,
	var values: List<String?>? = null
)

enum class ReportIndicatorArithmetic(val code: String) {
	NONE("none"),
	COUNT("count"),
	SUMMARY("sum"),
	AVERAGE("avg"),
	MAXIMUM("max"),
	MINIMUM("min")
}

class ReportIndicator(
	var columnId: SubjectDatasetColumnId? = null,
	var name: String? = null,
	var arithmetic: ReportIndicatorArithmetic? = ReportIndicatorArithmetic.NONE
)

class ReportDimension(
	var columnId: SubjectDatasetColumnId? = null,
	var name: String? = null
)

class Report(
	var reportId: ReportId? = null,
	var name: StandardTupleName? = null,
	var subjectId: SubjectId? = null,
	var connectId: ConnectedSpaceId? = null,
	var funnels: List<ReportFunnel>? = null,
	var indicators: List<ReportIndicator>? = null,
	var dimensions: List<ReportDimension>? = null,
	var filters: ParameterJoint? = null,
	var description: String? = null,
	var rect: GraphicRect? = null,
	var chart: Chart<ChartSettings>? = null,
	var simulating: Boolean? = false,
	var simulateData: DataResultSet? = null,
	// base64
	var simulateThumbnail: String? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override val lastVisitTime: Date? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple, Auditable, LastVisit