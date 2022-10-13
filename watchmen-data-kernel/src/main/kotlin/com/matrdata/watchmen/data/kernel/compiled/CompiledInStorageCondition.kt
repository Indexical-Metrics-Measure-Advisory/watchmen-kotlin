package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RuntimeVariables
import com.matrdata.watchmen.model.common.Condition

sealed interface CompiledInStorageCondition<C : Condition> {
	fun run(variables: RuntimeVariables, principal: Principal): Any?
}
