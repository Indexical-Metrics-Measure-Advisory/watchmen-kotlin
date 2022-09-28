package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RunnableContext
import com.matrdata.watchmen.model.common.Condition

sealed interface CompiledInMemoryCondition<C : Condition> {
	fun test(variables: RunnableContext, principal: Principal): Boolean
}
