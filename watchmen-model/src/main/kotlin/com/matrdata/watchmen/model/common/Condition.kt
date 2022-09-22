package com.matrdata.watchmen.model.common

sealed interface Condition : DataModel

enum class ParameterJointType(val code: String) {
	AND("and"),
	OR("or")
}

data class Joint(
	var jointType: ParameterJointType? = ParameterJointType.AND,
	var filters: List<Condition>? = null
) : Condition

sealed interface ParameterExpressionOperator {
	val code: String
}

sealed class Expression<OP : ParameterExpressionOperator>(
	open var left: Parameter? = null,
	open var operator: OP? = null,
) : Condition

enum class SingleParameterExpressionOperator(override val code: String) : ParameterExpressionOperator {
	EMPTY("empty"),
	NOT_EMPTY("not-empty")
}

data class SingleExpression(
	override var left: Parameter? = null,
	override var operator: SingleParameterExpressionOperator? = SingleParameterExpressionOperator.EMPTY,
) : Expression<SingleParameterExpressionOperator>()

enum class DualParameterExpressionOperator(override val code: String) : ParameterExpressionOperator {
	EQUALS("equals"),
	NOT_EQUALS("not-equals"),
	LESS("less"),
	LESS_EQUALS("less-equals"),
	MORE("more"),
	MORE_EQUALS("more-equals"),
	IN("in"),
	NOT_IN("not-in")
}

data class DualExpression(
	override var left: Parameter? = null,
	override var operator: DualParameterExpressionOperator? = DualParameterExpressionOperator.EQUALS,
	var right: Parameter? = null,
) : Expression<DualParameterExpressionOperator>()

interface Conditional {
	var conditional: Boolean?
	var on: Joint?
}
