package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

sealed interface CompiledInMemoryCondition<C : Condition> {
	fun getCondition(): C
	fun test(variables: PipelineVariables, principal: Principal): Boolean
}
