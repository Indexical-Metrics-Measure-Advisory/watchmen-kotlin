package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RunnableContext
import com.matrdata.watchmen.model.common.Parameter

sealed interface CompiledInMemoryParameter<P : Parameter> {
	fun value(variables: RunnableContext, principal: Principal): Any?
}
