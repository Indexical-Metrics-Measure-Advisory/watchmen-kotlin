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
}

sealed interface ConditionalParameter : Parameter, Conditional

sealed interface TopicFactorParameter : Parameter {
	var topicId: TopicId?
	var factorId: FactorId?
}

abstract class AbstractTopicFactorParameter : TopicFactorParameter {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.TOPIC
		set(value) {}
}

data class RegularTopicFactorParameter(
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null
) : AbstractTopicFactorParameter()

data class ConditionalTopicFactorParameter(
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null,
	override var conditional: Boolean? = true,
	override var on: Joint? = null
) : AbstractTopicFactorParameter(), ConditionalParameter

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

sealed interface ConstantParameter : Parameter {
	var value: String?
}

abstract class AbstractConstantParameter : ConstantParameter {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.CONSTANT
		set(value) {}
}

data class RegularConstantParameter(
	override var value: String? = null
) : AbstractConstantParameter()

data class ConditionalConstantParameter(
	override var value: String? = null,
	override var conditional: Boolean? = true,
	override var on: Joint? = null
) : AbstractConstantParameter(), ConditionalParameter

sealed interface ParameterComputeType {
	val code: String
}

enum class RegularParameterComputeType(override val code: String) : ParameterComputeType {
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
	DAY_OF_WEEK("day-of-week")
}

enum class CaseThenParameterComputeType(override val code: String) : ParameterComputeType {
	CASE_THEN("case-then")
}

sealed interface ComputedParameter<SubParameter : Parameter, PCT : ParameterComputeType> : Parameter {
	var type: PCT?
	var parameters: List<SubParameter>?
}

data class RegularComputedParameter(
	override var type: RegularParameterComputeType? = null,
	override var parameters: List<Parameter>? = null
) : ComputedParameter<Parameter, RegularParameterComputeType> {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.COMPUTED
		set(value) {}
}

data class CaseThenParameter(
	override var parameters: List<ConditionalParameter>? = null
) : ComputedParameter<ConditionalParameter, CaseThenParameterComputeType> {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.COMPUTED
		set(value) {}

	@Suppress("UNUSED_PARAMETER")
	override var type: CaseThenParameterComputeType?
		get() = CaseThenParameterComputeType.CASE_THEN
		set(value) {}
}
