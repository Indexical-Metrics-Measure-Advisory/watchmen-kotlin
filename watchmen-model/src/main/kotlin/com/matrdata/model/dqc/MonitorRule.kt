package com.matrdata.model.dqc

import com.matrdata.model.base.*
import java.util.*

enum class MonitorRuleCode(val code: String) {
	// structure
	RAW_MISMATCH_STRUCTURE("raw-mismatch-structure"),

	// type
	FACTOR_MISMATCH_ENUM("factor-mismatch-enum"),
	FACTOR_MISMATCH_TYPE("factor-mismatch-type"),
	FACTOR_MISMATCH_DATE_TYPE("factor-mismatch-date-type"),

	// topic row count
	ROWS_NOT_EXISTS("rows-not-exists"),
	ROWS_NO_CHANGE("rows-no-change"),
	ROWS_COUNT_MISMATCH_AND_ANOTHER("rows-count-mismatch-and-another"),

	// for all factor types
	FACTOR_IS_EMPTY("factor-is-empty"),
	FACTOR_USE_CAST("factor-use-cast"),
	FACTOR_COMMON_VALUE_OVER_COVERAGE("factor-common-value-over-coverage"),
	FACTOR_EMPTY_OVER_COVERAGE("factor-empty-over-coverage"),

	// for number type
	FACTOR_BREAKS_MONOTONE_INCREASING("factor-breaks-monotone-increasing"),
	FACTOR_BREAKS_MONOTONE_DECREASING("factor-breaks-monotone-decreasing"),
	FACTOR_NOT_IN_RANGE("factor-not-in-range"),
	FACTOR_MAX_NOT_IN_RANGE("factor-max-not-in-range"),
	FACTOR_MIN_NOT_IN_RANGE("factor-min-not-in-range"),
	FACTOR_AVG_NOT_IN_RANGE("factor-avg-not-in-range"),
	FACTOR_MEDIAN_NOT_IN_RANGE("factor-median-not-in-range"),
	FACTOR_QUANTILE_NOT_IN_RANGE("factor-quantile-not-in-range"),
	FACTOR_STDEV_NOT_IN_RANGE("factor-stdev-not-in-range"),
	FACTOR_COMMON_VALUE_NOT_IN_RANGE("factor-common-value-not-in-range"),

	// for string type
	FACTOR_IS_BLANK("factor-is-blank"),
	FACTOR_STRING_LENGTH_MISMATCH("factor-string-length-mismatch"),
	FACTOR_STRING_LENGTH_NOT_IN_RANGE("factor-string-length-not-in-range"),
	FACTOR_MATCH_REGEXP("factor-match-regexp"),
	FACTOR_MISMATCH_REGEXP("factor-mismatch-regexp"),

	// for 2 factors
	FACTOR_AND_ANOTHER("factor-and-another")
}

enum class MonitorRuleGrade(val code: String) {
	GLOBAL("global"),
	TOPIC("topic"),
	FACTOR("factor")
}

enum class MonitorRuleSeverity(val code: String) {
	FATAL("fatal"),
	WARN("warn"),
	TRACE("trace")
}

enum class MonitorRuleStatisticalInterval(val code: String) {
	DAILY("daily"),
	WEEKLY("weekly"),
	MONTHLY("monthly")
}

enum class MonitorRuleCompareOperator(val code: String) {
	EQUAL("eq"),
	LESS_THAN("lt"),
	LESS_THAN_OR_EQUAL("lte"),
	GREATER_THAN("gt"),
	GREATER_THAN_EQUAL("gte")
}

class MonitorRuleParameters(
	var statisticalInterval: MonitorRuleStatisticalInterval? = null,
	var coverageRate: Int? = null,
	var aggregation: Int? = null,
	var quantile: Int? = null,
	var length: Int? = null,
	var max: Int? = null,
	var min: Int? = null,
	var regexp: String? = null,
	var compareOperator: MonitorRuleCompareOperator? = null,
	var topicId: TopicId? = null,
	var factorId: FactorId? = null
)

class MonitorRule(
	var ruleId: MonitorRuleId? = null,
	var code: MonitorRuleCode? = null,
	var grade: MonitorRuleGrade? = null,
	var severity: MonitorRuleSeverity? = null,
	var topicId: TopicId? = null,
	var factorId: FactorId? = null,
	var params: MonitorRuleParameters? = null,
	var enabled: Boolean? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var tenantId: TenantId? = null
) : TenantBasedTuple