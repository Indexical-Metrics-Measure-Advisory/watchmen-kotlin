package com.matrdata.model.common

enum class ParameterJointType(val code: String) {
    AND("and"),
    OR("or")
}

class ParameterJoint(
    var jointType: ParameterJointType = ParameterJointType.AND,
    var filters: List<ParameterCondition>? = mutableListOf()
) : ParameterCondition