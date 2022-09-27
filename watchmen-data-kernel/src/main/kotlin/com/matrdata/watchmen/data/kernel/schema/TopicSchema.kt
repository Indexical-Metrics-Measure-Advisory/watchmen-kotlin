package com.matrdata.watchmen.data.kernel.schema

import com.matrdata.watchmen.model.admin.Factor
import com.matrdata.watchmen.model.admin.Topic
import com.matrdata.watchmen.model.common.FactorId

class TopicSchema(val topic: Topic) {
	fun findFactorById(factorId: FactorId): Factor? {
		return this.topic.factors?.find { factor -> factor.factorId == factorId }
	}
}