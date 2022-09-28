package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RunnableContext
import com.matrdata.watchmen.data.kernel.utils.askFactorById
import com.matrdata.watchmen.data.kernel.utils.askTopicSchemaById
import com.matrdata.watchmen.model.common.TopicFactorParameter

/**
 * compiled in-memory topic factor parameter
 */
class CompiledInMemoryTopicFactorParameter constructor(
	val parameter: TopicFactorParameter
) : CompiledInMemoryParameter<TopicFactorParameter> {
	override fun value(variables: RunnableContext, principal: Principal): Any? {
		val topicSchema = askTopicSchemaById(this.parameter.topicId, principal) { "parameter[${this.parameter}]" }
		var factor = askFactorById(topicSchema, this.parameter.factorId) { "parameter[${this.parameter}]" }
		TODO("Not yet implemented")
	}
}