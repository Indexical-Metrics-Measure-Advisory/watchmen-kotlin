package com.matrdata.watchmen.model.common

sealed interface ParameterCondition : DataModel

enum class ParameterJointType(val code: String) {
	AND("and"),
	OR("or")
}

data class ParameterJoint(
	var jointType: ParameterJointType? = ParameterJointType.AND,
	var filters: List<ParameterCondition>? = null
) : ParameterCondition

sealed interface ParameterExpressionOperator {
	val code: String
}

sealed class ParameterExpression<OP : ParameterExpressionOperator>(
	open var left: Parameter? = null,
	open var operator: OP? = null,
) : ParameterCondition

enum class SingleParameterExpressionOperator(override val code: String) : ParameterExpressionOperator {
	EMPTY("empty"),
	NOT_EMPTY("not-empty")
}

data class SingleParameterExpression(
	override var left: Parameter? = null,
	override var operator: SingleParameterExpressionOperator? = SingleParameterExpressionOperator.EMPTY,
) : ParameterExpression<SingleParameterExpressionOperator>()

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

data class DualParameterExpression(
	override var left: Parameter? = null,
	override var operator: DualParameterExpressionOperator? = DualParameterExpressionOperator.EQUALS,
	var right: Parameter? = null,
) : ParameterExpression<DualParameterExpressionOperator>()