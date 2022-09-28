package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.common.TopicFactorParameter

/**
 * compiled in-memory topic factor parameter
 */
class CompiledInMemoryTopicFactorParameter constructor(
	val parameter: TopicFactorParameter
) : CompiledInMemoryParameter<TopicFactorParameter> {
	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}