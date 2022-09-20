package com.matrdata.watchmen.model.indicator

import com.matrdata.watchmen.model.common.*
import com.matrdata.watchmen.utils.assert
import com.matrdata.watchmen.utils.throwIfFalse
import java.time.LocalDateTime

enum class BucketType(val code: String) {
	VALUE("value"),
	VALUE_MEASURE("value-measure"),
	CATEGORY_MEASURE("category-measure"),
	ENUM_MEASURE("enum-measure")
}

enum class RangeBucketValueIncluding(val code: String) {
	INCLUDE_MIN("include-min"),
	INCLUDE_MAX("include-max")
}

typealias SegmentValue = Any

sealed interface BucketSegment<V : SegmentValue> : DataModel {
	var name: String?
	var value: V?
}

data class NumericSegmentValue(
	var min: String? = null,
	var max: String? = null
) : DataModel

data class NumericValueSegment(
	override var name: String? = null,
	override var value: NumericSegmentValue? = null
) : BucketSegment<NumericSegmentValue>


sealed interface Bucket<S : BucketSegment<out SegmentValue>> : TenantBasedTuple, OptimisticLock {
	var bucketId: BucketId?
	var name: String?
	var type: BucketType?
	var segments: List<S>?
	var description: String?
}

sealed interface NumericSegmentsHolder : Bucket<NumericValueSegment> {
	var include: RangeBucketValueIncluding?
	override var segments: List<NumericValueSegment>?
}

data class NumericValueBucket(
	override var bucketId: BucketId? = null,
	override var name: String? = null,
	override var include: RangeBucketValueIncluding? = null,
	override var segments: List<NumericValueSegment>? = null,
	override var description: String? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : NumericSegmentsHolder {
	@Suppress("UNUSED_PARAMETER")
	override var type: BucketType?
		get() = BucketType.VALUE
		set(value) {}
}

sealed interface MeasureBucket<S : BucketSegment<out SegmentValue>> : Bucket<S> {
	var measure: MeasureMethod?
}

private fun checkMeasureMethod(measure: MeasureMethod?, validMeasureMethods: List<MeasureMethod>) {
	measure
		?.assert { validMeasureMethods.contains(this) }
		?.throwIfFalse { IllegalArgumentException("[${validMeasureMethods}] are supported, current is [$measure].") }
}

data class NumericValueMeasureBucket(
	override var bucketId: BucketId? = null,
	override var name: String? = null,
	override var measure: MeasureMethod? = null,
	override var include: RangeBucketValueIncluding? = null,
	override var segments: List<NumericValueSegment>? = null,
	override var description: String? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : MeasureBucket<NumericValueSegment>, NumericSegmentsHolder {
	companion object {
		val AVAILABLE_MEASURES =
			listOf(MeasureMethod.FLOOR, MeasureMethod.RESIDENTIAL_AREA, MeasureMethod.AGE, MeasureMethod.BIZ_SCALE)
	}

	@Suppress("UNUSED_PARAMETER")
	override var type: BucketType?
		get() = BucketType.VALUE_MEASURE
		set(value) {}

	init {
		checkMeasureMethod(measure, AVAILABLE_MEASURES)
	}
}

data class CategorySegment(
	override var name: String? = null,
	override var value: List<String>? = null
) : BucketSegment<List<String>> {
	companion object {
		const val OTHER_CATEGORY_SEGMENT = "&others"
	}
}

sealed interface CategorySegmentsHolder : Bucket<CategorySegment> {
	override var segments: List<CategorySegment>?
}

data class CategoryMeasureBucket(
	override var bucketId: BucketId? = null,
	override var name: String? = null,
	override var measure: MeasureMethod? = null,
	override var segments: List<CategorySegment>? = null,
	override var description: String? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null,
) : CategorySegmentsHolder, MeasureBucket<CategorySegment> {
	companion object {
		val AVAILABLE_MEASURES =
			listOf(
				MeasureMethod.CONTINENT, MeasureMethod.REGION, MeasureMethod.COUNTRY,
				MeasureMethod.PROVINCE, MeasureMethod.CITY, MeasureMethod.DISTRICT,
				MeasureMethod.RESIDENCE_TYPE,
				MeasureMethod.GENDER, MeasureMethod.OCCUPATION, MeasureMethod.RELIGION, MeasureMethod.NATIONALITY,
				MeasureMethod.BIZ_TRADE,
				MeasureMethod.BOOLEAN
			)
	}

	@Suppress("UNUSED_PARAMETER")
	override var type: BucketType?
		get() = BucketType.CATEGORY_MEASURE
		set(value) {}

	init {
		checkMeasureMethod(measure, AVAILABLE_MEASURES)
	}
}

data class EnumMeasureBucket(
	override var bucketId: BucketId? = null,
	override var name: String? = null,
	override var segments: List<CategorySegment>? = null,
	var enumId: EnumId? = null,
	override var description: String? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : CategorySegmentsHolder, MeasureBucket<CategorySegment> {
	@Suppress("UNUSED_PARAMETER")
	override var type: BucketType?
		get() = BucketType.ENUM_MEASURE
		set(value) {}

	@Suppress("UNUSED_PARAMETER")
	override var measure: MeasureMethod?
		get() = MeasureMethod.ENUM
		set(value) {}
}