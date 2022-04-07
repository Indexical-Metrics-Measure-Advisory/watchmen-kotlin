package com.matrdata.model.indicator

import com.matrdata.model.base.*
import java.util.*

enum class IndicatorAggregateArithmetic(val code: String) {
	COUNT("count"),
	SUM("sum"),
	AVG("avg"),
	MAX("max"),
	MIN("min")
}

enum class InspectMeasureOn(val code: String) {
	NONE("none"),
	VALUE("value"),
	OTHER("other")
}

enum class InspectionTimeRangeType(val code: String) {
	YEAR("year"),
	HALF_YEAR("half-year"),
	QUARTER("quarter"),
	MONTH("month"),
	HALF_MONTH("half-month"),
	TEN_DAYS("ten-days"),
	WEEK_OF_YEAR("week-of-year"),
	WEEK_OF_MONTH("week-of-month"),
	HALF_WEEK("half-week"),
	DAY_OF_MONTH("day-of-month"),
	DAY_OF_WEEK("day-of-week"),
	DAY_KIND("day-kind"),
	HOUR("hour"),
	HOUR_KIND("hour-kind"),
	AM_PM("am-pm")
}

interface InspectionTimeRange<V : Any> {
	var type: InspectionTimeRangeType?
	var value: V?
}

class InspectionYearRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.YEAR,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2
class InspectionHalfYearRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.HALF_YEAR,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2, 3, 4
class InspectionQuarterRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.QUARTER,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
class InspectionMonthRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.MONTH,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2
class InspectionHalfMonthRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.HALF_MONTH,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2, 3
class InspectionTenDaysRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.TEN_DAYS,
	override var value: Int?
) : InspectionTimeRange<Int>

// 0 - 53
class InspectionWeekOfYearRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.WEEK_OF_YEAR,
	override var value: Int?
) : InspectionTimeRange<Int>

// 0, 1, 2, 3, 4, 5
class InspectionWeekOfMonthRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.WEEK_OF_MONTH,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2
class InspectionHalfWeekRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.HALF_WEEK,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1 - 31
class InspectionDayOfMonthRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.DAY_OF_MONTH,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2, 3, 4, 5, 6, 7
class InspectionDayOfWeekRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.DAY_OF_WEEK,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2, 3
class InspectionDayKindRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.DAY_KIND,
	override var value: Int?
) : InspectionTimeRange<Int>

// 0 - 23
class InspectionHourRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.HOUR,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2, 3
class InspectionHourKindRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.HOUR_KIND,
	override var value: Int?
) : InspectionTimeRange<Int>

// 1, 2
class InspectionAmPmRange(
	override var type: InspectionTimeRangeType? = InspectionTimeRangeType.AM_PM,
	override var value: Int?
) : InspectionTimeRange<Int>

class Inspection(
	var inspectionId: InspectionId? = null,
	var name: StandardTupleName? = null,
	var indicatorId: IndicatorId? = null,
	// indicator value aggregate arithmetic
	var aggregateArithmetics: List<IndicatorAggregateArithmetic> = mutableListOf(),
	// none, measure on indicator value or other factor
	var measureOn: InspectMeasureOn? = null,
	// if measure on factor, factor id must be given
	var measureOnFactorId: FactorId? = null,
	// bucket for any measure on type, or no bucket also allowed if measure on factor rather than indicator value
	var measureOnBucketId: BucketId? = null,
	// time range
	var timeRangeMeasure: MeasureMethod? = null,
	// time range factor
	var timeRangeFactorId: FactorId? = null,
	// ranges on time factor for filter data
	var timeRanges: List<InspectionTimeRange<Any>>? = null,
	// time measure on factor. measure can use another factor or just measure on the same time factor
	var measureOnTime: MeasureMethod? = null,
	// time measure on factor
	var measureOnTimeFactorId: FactorId? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple, Auditable
