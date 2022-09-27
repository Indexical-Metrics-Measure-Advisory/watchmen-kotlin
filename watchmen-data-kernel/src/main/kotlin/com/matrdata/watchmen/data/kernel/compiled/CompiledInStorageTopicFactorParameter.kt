package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.common.TopicFactorParameter

/**
 * compiled in-storage topic factor parameter
 */
class CompiledInStorageTopicFactorParameter constructor(
	private val parameter: TopicFactorParameter
) : CompiledInStorageParameter<TopicFactorParameter> {
	override fun getParameter(): TopicFactorParameter {
		return this.parameter
	}

	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}