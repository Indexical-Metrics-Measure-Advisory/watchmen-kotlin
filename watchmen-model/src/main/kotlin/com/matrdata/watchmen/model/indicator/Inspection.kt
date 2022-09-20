package com.matrdata.watchmen.model.indicator

import com.matrdata.watchmen.model.admin.DateTimeFactorValueValidators
import com.matrdata.watchmen.model.admin.DateTimeFactorValuesValidator
import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class InspectMeasureOnType(val code: String) {
	NONE("none"),
	VALUE("value"),
	OTHER("other")
}

data class InspectMeasureOn(
	// none, measure on indicator value or other factor
	var type: InspectMeasureOnType? = null,
	// if measure on factor, factor id must be given
	var factorId: FactorId? = null,
	// bucket for any measure on type, or no bucket also allowed if measure on factor rather than indicator value
	var bucketId: BucketId? = null
) : DataModel

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

sealed interface InspectionTimeRange<V : Any> : DataModel {
	var type: InspectionTimeRangeType?
	var value: V?
}

data class InspectionYearRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.YEAR
		set(value) {}
}

private fun checkDateTimeRange(value: Int?, validator: DateTimeFactorValuesValidator) {
	value?.let {
		validator.isValidOrThrow(it) { range -> "$range is supported, currently is [$value]." }
	}
}

data class InspectionHalfYearRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.HALF_YEAR
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.HALF_YEAR)
	}
}

data class InspectionQuarterRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.QUARTER
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.QUARTER)
	}
}

data class InspectionMonthRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.MONTH
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.MONTH)
	}
}

data class InspectionHalfMonthRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.HALF_MONTH
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.HALF_MONTH)
	}
}

data class InspectionTenDaysRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.TEN_DAYS
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.TEN_DAYS)
	}
}

data class InspectionWeekOfYearRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.WEEK_OF_YEAR
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.WEEK_OF_YEAR)
	}
}

data class InspectionWeekOfMonthRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.WEEK_OF_MONTH
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.WEEK_OF_MONTH)
	}
}

data class InspectionHalfWeekRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.HALF_WEEK
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.HALF_WEEK)
	}
}

data class InspectionDayOfMonthRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.DAY_OF_MONTH
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.DAY_OF_MONTH)
	}
}

data class InspectionDayOfWeekRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.DAY_OF_WEEK
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.DAY_OF_WEEK)
	}
}

data class InspectionDayKindRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.DAY_KIND
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.DAY_KIND)
	}
}

class InspectionHourRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.HOUR
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.HOUR)
	}
}

data class InspectionHourKindRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.HOUR_KIND
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.HOUR_KIND)
	}
}

data class InspectionAmPmRange(
	override var value: Int? = null
) : InspectionTimeRange<Int> {
	@Suppress("UNUSED_PARAMETER")
	override var type: InspectionTimeRangeType?
		get() = InspectionTimeRangeType.AM_PM
		set(value) {}

	init {
		checkDateTimeRange(value, DateTimeFactorValueValidators.AM_PM)
	}
}

data class Inspection(
	var inspectionId: InspectionId? = null,
	var name: String? = null,
	var indicatorId: IndicatorId? = null,
	// indicator value aggregate arithmetic
	var aggregateArithmetics: List<IndicatorAggregateArithmetic>? = null,
	var measures: List<InspectMeasureOn>? = null,
	// time range
	var timeRangeMeasure: MeasureMethod? = null,
	// time range factor
	var timeRangeFactorId: FactorId? = null,
	// ranges on time factor for filter data
	var timeRanges: List<InspectionTimeRange<out Any>>? = null,
	// time measure on factor. measure can use another factor or just measure on the same time factor
	var measureOnTime: MeasureMethod? = null,
	// time measure on factor
	var measureOnTimeFactorId: FactorId? = null,
	var criteria: List<IndicatorCriteria>? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock
