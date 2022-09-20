package com.matrdata.watchmen.model.indicator

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class IndicatorAggregateArithmetic(val code: String) {
	COUNT("count"),
	SUM("sum"),
	AVG("avg"),
	MAX("max"),
	MIN("min")
}

enum class RelevantIndicatorType(val code: String) {
	SAME("same"),
	HIGH_CORRELATED("high-correlated"),
	WEAK_CORRELATED("weak-correlated"),

	/** this causes relevant */
	THIS_CAUSES_RELEVANT("this-causes-relevant"),

	/** relevant causes this */
	RELEVANT_CAUSES_THIS("relevant-causes-this")
}

data class RelevantIndicator(
	var indicatorId: IndicatorId? = null,
	var type: RelevantIndicatorType? = null
) : DataModel

enum class IndicatorBaseOn(val code: String) {
	TOPIC("topic"),
	SUBJECT("subject")
}

data class IndicatorFilter(
	var enabled: Boolean? = false,
	var joint: ParameterJoint? = null
) : DataModel

data class Indicator(
	var indicatorId: IndicatorId? = null,
	var name: String? = null,
	// when indicator is on topic
	var topicOrSubjectId: TopicOrSubjectId? = null,
	// is a count indicator when factor is not declared
	// it is columnId when base one a subject
	var factorId: FactorOrSubjectDatasetColumnId? = null,

	// only count can be applied when factor id is not declared
	var aggregateArithmetic: IndicatorAggregateArithmetic? = null,
	var baseOn: IndicatorBaseOn? = null,
	var category1: String? = null,
	var category2: String? = null,
	var category3: String? = null,
	var description: String? = null,

	// effective only when factorId is appointed
	var valueBuckets: List<BucketId>? = null,

	var relevants: List<RelevantIndicator>? = null,
	var filter: IndicatorFilter? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock
