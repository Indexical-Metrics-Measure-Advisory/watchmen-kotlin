package com.matrdata.model.indicator

import com.matrdata.model.base.*
import java.util.*

enum class RelevantIndicatorType(val code: String) {
	SAME("same"),
	HIGH_CORRELATED("high-correlated"),
	WEAK_CORRELATED("weak-correlated"),

	/** this causes relevant */
	THIS_CAUSES_RELEVANT("this-causes-relevant"),

	/** relevant causes this */
	RELEVANT_CAUSES_THIS("relevant-causes-this")
}

class RelevantIndicator(
	var indicatorId: IndicatorId? = null,
	var type: RelevantIndicatorType? = null
)

class Indicator(
	var indicatorId: IndicatorId? = null,
	var name: StandardTupleName? = null,
	var topicId: TopicId? = null,
	// is a count indicator when factor is not appointed
	var factorId: FactorId? = null,
	var category1: String? = null,
	var category2: String? = null,
	var category3: String? = null,
	var description: String? = null,
	// effective only when factorId is appointed
	var valueBuckets: List<BucketId>? = null,
	var relevants: List<RelevantIndicator>? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : TenantBasedTuple, OptimisticLock
