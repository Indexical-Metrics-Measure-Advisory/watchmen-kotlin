package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.FactorType
import com.matrdata.watchmen.model.common.TopicFactorParameter
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

/**
 * compiled in-memory topic factor parameter
 */
class CompiledInMemoryTopicFactorParameter constructor(
	val parameter: TopicFactorParameter,
	override val possibleTypes: List<FactorType>,
	val askValue: (PipelineVariables, Principal) -> Any?,
	override val dependedDefs: List<Any>
) : CompiledInMemoryParameter<TopicFactorParameter> {
	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		// use compiled function to retrieve value
		return this.askValue(variables, principal)
	}
}