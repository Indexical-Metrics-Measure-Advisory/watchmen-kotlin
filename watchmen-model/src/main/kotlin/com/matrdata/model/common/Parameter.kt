package com.matrdata.model.common

enum class ParameterKind(val code: String) {
    TOPIC("topic"),
    CONSTANT("constant"),
    COMPUTED("computed")
}

abstract class Parameter(
    open var kind: ParameterKind? = null,
    var conditional: Boolean? = false,
    var on: ParameterJoint? = null
)