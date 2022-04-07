package com.matrdata.model.common

enum class ParameterExpressionOperator(val code: String) {
    EMPTY("empty"),
    NOT_EMPTY("not-empty"),
    EQUALS("equals"),
    NOT_EQUALS("not-equals"),
    LESS("less"),
    LESS_EQUALS("less-equals"),
    MORE("more"),
    MORE_EQUALS("more-equals"),
    IN("in"),
    NOT_IN("not-in")
}

class ParameterExpression(
    var left: Parameter? = null,
    var operator: ParameterExpressionOperator = ParameterExpressionOperator.EQUALS,
    var right: Parameter? = null
) : ParameterCondition