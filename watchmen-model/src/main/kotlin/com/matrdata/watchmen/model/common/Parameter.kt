package com.matrdata.watchmen.model.common

enum class ParameterKind(val code: String) {
	TOPIC("topic"),
	CONSTANT("constant"),
	COMPUTED("computed")
}

/**
 * parameter might with condition.
 */
sealed interface Parameter : DataModel {
	var kind: ParameterKind?
	var conditional: Boolean?
	var on: ParameterJoint?
}

data class TopicFactorParameter(
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null,
	var topicId: TopicId? = null,
	var factorId: FactorId? = null
) : Parameter {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.TOPIC
		set(value) {}
}

enum class VariablePredefineFunctions(val code: String) {
	NEXT_SEQ("&nextSeq"),
	COUNT("&count"),
	LENGTH("&length"),
	SUM("&sum"),
	FROM_PREVIOUS_TRIGGER_DATA("&old"),

	DAY_DIFF("&dayDiff"),
	MONTH_DIFF("&monthDiff"),
	YEAR_DIFF("&yearDiff"),
	DATE_FORMAT("&fmtDate"),
	NOW("&now")
}

data class ConstantParameter(
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null,
	var value: String? = null
) : Parameter {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.CONSTANT
		set(value) {}
}

enum class ParameterComputeType(val code: String) {
	NONE("none"),
	ADD("add"),
	SUBTRACT("subtract"),
	MULTIPLY("multiply"),
	DIVIDE("divide"),
	MODULUS("modulus"),
	YEAR_OF("year-of"),
	HALF_YEAR_OF("half-year-of"),
	QUARTER_OF("quarter-of"),
	MONTH_OF("month-of"),
	WEEK_OF_YEAR("week-of-year"),
	WEEK_OF_MONTH("week-of-month"),
	DAY_OF_MONTH("day-of-month"),
	DAY_OF_WEEK("day-of-week"),
	CASE_THEN("case-then")
}

data class ComputedParameter(
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null,
	var type: ParameterComputeType? = null,
	var parameters: List<Parameter>? = null
) : Parameter {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.COMPUTED
		set(value) {}
}
