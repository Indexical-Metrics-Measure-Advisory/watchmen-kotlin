package com.matrdata.model.common

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

class ComputedParameter(
    override var kind: ParameterKind? = ParameterKind.COMPUTED,
    var type: ParameterComputeType = ParameterComputeType.NONE,
    var parameters: List<Parameter>? = mutableListOf()
) : Parameter(kind)