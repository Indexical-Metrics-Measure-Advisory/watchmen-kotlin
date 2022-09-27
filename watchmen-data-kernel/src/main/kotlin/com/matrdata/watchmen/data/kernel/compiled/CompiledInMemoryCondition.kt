package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.common.Condition

sealed interface CompiledInMemoryCondition<C : Condition> {
	fun getCondition(): C
	fun test(variables: PipelineVariables, principal: Principal): Boolean
}