package com.matrdata.watchmen.model.common

enum class ParameterKind(val code: String) {
	TOPIC("topic"),
	CONSTANT("constant"),
	COMPUTED("computed")
}

sealed interface Parameter : DataModel {
	var kind: ParameterKind?
}

/**
 * parameter might with condition
 */
sealed interface ConditionalParameter : Parameter {
	var conditional: Boolean?
	var on: ParameterJoint?
}

sealed class TopicFactorParameter(
	open var topicId: TopicId? = null,
	open var factorId: FactorId? = null
) : Parameter {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.TOPIC
		set(value) {}
}

data class StandardTopicFactorParameter(
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null
) : TopicFactorParameter()

data class ConditionalTopicFactorParameter(
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null,
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null
) : TopicFactorParameter(), ConditionalParameter

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

sealed class ConstantParameter(
	open var value: String? = null
) : Parameter {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.CONSTANT
		set(value) {}
}

data class StandardConstantParameter(
	override var value: String? = null
) : ConstantParameter()

data class ConditionalConstantParameter(
	override var value: String? = null,
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null
) : ConstantParameter(), ConditionalParameter

sealed interface ParameterComputeType {
	val code: String
}

sealed class ComputedParameter<T : ParameterComputeType, P : Parameter>(
	open var type: T? = null,
	open var parameters: List<P>? = null
) : Parameter {
	@Suppress("UNUSED_PARAMETER")
	override var kind: ParameterKind?
		get() = ParameterKind.COMPUTED
		set(value) {}
}

sealed interface NonConditionalParameterComputeType : ParameterComputeType

enum class NoopComputeType(override val code: String) : NonConditionalParameterComputeType {
	NONE("none")
}

enum class MultipleArgumentsComputeType(override val code: String) : NonConditionalParameterComputeType {
	ADD("add"),
	SUBTRACT("subtract"),
	MULTIPLY("multiply"),
	DIVIDE("divide"),
	MODULUS("modulus")
}

enum class SingleArgumentComputeType(override val code: String) : NonConditionalParameterComputeType {
	YEAR_OF("year-of"),
	HALF_YEAR_OF("half-year-of"),
	QUARTER_OF("quarter-of"),
	MONTH_OF("month-of"),
	WEEK_OF_YEAR("week-of-year"),
	WEEK_OF_MONTH("week-of-month"),
	DAY_OF_MONTH("day-of-month"),
	DAY_OF_WEEK("day-of-week")
}

enum class ConditionalParameterComputeType(override val code: String) : ParameterComputeType {
	CASE_THEN("case-then")
}

data class StandardComputedNonConditionalParameter(
	override var type: NonConditionalParameterComputeType? = NoopComputeType.NONE,
	override var parameters: List<Parameter>? = null
) : ComputedParameter<NonConditionalParameterComputeType, Parameter>()

data class StandardComputedConditionalParameter(
	override var parameters: List<ConditionalParameter>? = null
) : ComputedParameter<ConditionalParameterComputeType, ConditionalParameter>() {
	@Suppress("UNUSED_PARAMETER")
	override var type: ConditionalParameterComputeType?
		get() = ConditionalParameterComputeType.CASE_THEN
		set(value) {}
}

data class ConditionalComputedNonConditionalParameter(
	override var type: NonConditionalParameterComputeType? = NoopComputeType.NONE,
	override var parameters: List<Parameter>? = null,
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null
) : ComputedParameter<NonConditionalParameterComputeType, Parameter>(), ConditionalParameter

data class ConditionalComputedConditionalParameter(
	override var parameters: List<ConditionalParameter>? = null,
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null
) : ComputedParameter<ConditionalParameterComputeType, ConditionalParameter>(), ConditionalParameter {
	@Suppress("UNUSED_PARAMETER")
	override var type: ConditionalParameterComputeType?
		get() = ConditionalParameterComputeType.CASE_THEN
		set(value) {}
}
