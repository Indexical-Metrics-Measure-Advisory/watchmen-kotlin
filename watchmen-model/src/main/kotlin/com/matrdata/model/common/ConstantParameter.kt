package com.matrdata.model.common

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

class ConstantParameter(
    override var kind: ParameterKind? = ParameterKind.CONSTANT,
    var value: String? = null
) : Parameter(kind)