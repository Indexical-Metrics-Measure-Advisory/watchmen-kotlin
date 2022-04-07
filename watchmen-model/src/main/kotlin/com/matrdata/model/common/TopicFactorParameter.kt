package com.matrdata.model.common

import com.matrdata.model.base.FactorId
import com.matrdata.model.base.TopicId

class TopicFactorParameter(
    override var kind: ParameterKind? = ParameterKind.TOPIC,
    var topicId: TopicId? = null,
    var factorId: FactorId? = null
) : Parameter(kind)