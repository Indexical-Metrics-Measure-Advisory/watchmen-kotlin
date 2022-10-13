package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RuntimeVariables
import com.matrdata.watchmen.model.common.TopicFactorParameter

/**
 * compiled in-storage topic factor parameter
 */
class CompiledInStorageTopicFactorParameter constructor(
	val parameter: TopicFactorParameter
) : CompiledInStorageParameter<TopicFactorParameter> {
	override fun value(variables: RuntimeVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}