package com.matrdata.model.indicator

import com.matrdata.model.base.*
import java.util.*

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

open class BucketSegment<V : Any>(
	open var name: String? = null,
	open var value: V? = null
)

interface Bucket<V : Any, Segment : BucketSegment<V>> : TenantBasedTuple, OptimisticLock {
	var bucketId: BucketId?
	var name: StandardTupleName?
	var type: BucketType?
	var segments: List<Segment>?
	var description: String?
}

class NumericSegmentValue(
	var min: String? = null,
	var max: String? = null
)

class NumericValueSegment(
	override var name: String? = null,
	override var value: NumericSegmentValue? = null
) : BucketSegment<NumericSegmentValue>(name, value)

interface NumericSegmentsHolder : Bucket<NumericSegmentValue, NumericValueSegment> {
	var include: RangeBucketValueIncluding?
}

class NumericValueBucket(
	override var bucketId: BucketId? = null,
	override var name: StandardTupleName? = null,
	override var type: BucketType? = BucketType.VALUE,
	override var segments: List<NumericValueSegment>? = null,
	override var description: String? = null,
	override var include: RangeBucketValueIncluding? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : NumericSegmentsHolder

interface MeasureBucket<V : Any, Segment : BucketSegment<V>> : Bucket<V, Segment> {
	var measure: MeasureMethod?
}

// MeasureMethod.FLOOR, MeasureMethod.RESIDENTIAL_AREA, MeasureMethod.AGE, MeasureMethod.BIZ_SCALE
class NumericValueMeasureBucket(
	override var bucketId: BucketId? = null,
	override var name: StandardTupleName? = null,
	override var type: BucketType? = BucketType.VALUE_MEASURE,
	override var segments: List<NumericValueSegment>?,
	override var description: String? = null,
	override var measure: MeasureMethod? = null,
	override var include: RangeBucketValueIncluding? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : MeasureBucket<NumericSegmentValue, NumericValueSegment>, NumericSegmentsHolder

val OtherCategorySegmentValue = "&others"

// can be OtherCategorySegmentValue
typealias CategorySegmentValue = List<String>

class CategorySegment(
	override var name: String? = null,
	override var value: CategorySegmentValue? = null
) : BucketSegment<CategorySegmentValue>(name, value)

interface CategorySegmentsHolder : Bucket<CategorySegmentValue, CategorySegment>

// MeasureMethod.CONTINENT, MeasureMethod.REGION, MeasureMethod.COUNTRY,
// MeasureMethod.PROVINCE, MeasureMethod.CITY, MeasureMethod.DISTRICT,
// MeasureMethod.RESIDENCE_TYPE,
// MeasureMethod.GENDER, MeasureMethod.OCCUPATION, MeasureMethod.RELIGION, MeasureMethod.NATIONALITY,
// MeasureMethod.BIZ_TRADE,
// MeasureMethod.BOOLEAN
class CategoryMeasureBucket(
	override var bucketId: BucketId? = null,
	override var name: StandardTupleName? = null,
	override var type: BucketType? = BucketType.CATEGORY_MEASURE,
	override var segments: List<CategorySegment>? = null,
	override var description: String? = null,
	override var measure: MeasureMethod? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : MeasureBucket<CategorySegmentValue, CategorySegment>, CategorySegmentsHolder


class EnumMeasureBucket(
	override var bucketId: BucketId? = null,
	override var name: StandardTupleName? = null,
	override var type: BucketType? = BucketType.ENUM_MEASURE,
	override var segments: List<CategorySegment>? = null,
	override var description: String? = null,
	override var measure: MeasureMethod? = MeasureMethod.ENUM,
	var enumId: EnumId? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : MeasureBucket<CategorySegmentValue, CategorySegment>, CategorySegmentsHolder
