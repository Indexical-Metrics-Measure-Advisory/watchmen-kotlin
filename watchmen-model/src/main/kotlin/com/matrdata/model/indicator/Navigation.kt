package com.matrdata.model.indicator

import com.matrdata.model.base.*
import java.util.*

enum class NavigationTimeRangeType(val code: String) {
	YEAR("year"),
	MONTH("month")
}

interface NavigationIndicatorCriteria {
	var factorId: FactorId?
}

/**
 * fill when use predefined bucket
 */
class NavigationIndicatorCriteriaOnBucket(
	var bucketId: BucketId? = null,
	var bucketSegmentName: String? = null,
	override var factorId: FactorId? = null
) : NavigationIndicatorCriteria

enum class NavigationIndicatorCriteriaOperator(val code: String) {
	EQUALS("equals"),
	NOT_EQUALS("not-equals"),
	LESS("less"),
	LESS_EQUALS("less-equals"),
	MORE("more"),
	MORE_EQUALS("more-equals")
}

class NavigationIndicatorCriteriaOnExpression(
	var operator: NavigationIndicatorCriteriaOperator? = NavigationIndicatorCriteriaOperator.EQUALS,
	var value: String? = null,
	override var factorId: FactorId? = null
) : NavigationIndicatorCriteria

open class NavigationIndicator(
	open var indicatorId: IndicatorId? = null,
	open var name: String? = null,
	open var aggregateArithmetic: IndicatorAggregateArithmetic? = null,
	open var formula: String? = null,
	open var includeInFinalScore: Boolean? = true,
	open var criteria: List<NavigationIndicatorCriteria>? = mutableListOf(),
	open var variableName: String? = null,
)

val MANUAL_COMPUTE_NAVIGATION_INDICATOR_ID = "-1"

/**
 * 	for manual compute indicator,
 *  1. indicatorId fixed as {@link MANUAL_COMPUTE_NAVIGATION_INDICATOR_ID},
 *  2. aggregateArithmetics fixed as {@link IndicatorAggregateArithmetic#MAX}, will be ignored anyway in runtime
 *  3. criteria fixed as zero length array, will be ignored anyway in runtime
 */
class ManualComputeNavigationIndicator(
	override var indicatorId: IndicatorId? = MANUAL_COMPUTE_NAVIGATION_INDICATOR_ID,
	override var name: String? = null,
	override var aggregateArithmetic: IndicatorAggregateArithmetic? = IndicatorAggregateArithmetic.MAX,
	override var formula: String? = null,
	override var includeInFinalScore: Boolean? = true,
	override var criteria: List<NavigationIndicatorCriteria>? = mutableListOf(),
	override var variableName: String? = null
) : NavigationIndicator(indicatorId, name, aggregateArithmetic, formula, includeInFinalScore, criteria, variableName)

class Navigation(
	var navigationId: NavigationId? = null,
	var name: StandardTupleName? = null,
	var description: String? = null,
	var timeRangeType: NavigationTimeRangeType? = NavigationTimeRangeType.YEAR,
	var timeRangeYear: String? = null,
	var timeRangeMonth: String? = null,
	var compareWithPreviousTimeRange: Boolean? = false,
	var indicators: List<NavigationIndicator>? = mutableListOf(),
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple, Auditable
