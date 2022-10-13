package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RuntimeVariables
import com.matrdata.watchmen.model.common.Parameter

sealed interface CompiledInStorageParameter<P : Parameter> {
	fun value(variables: RuntimeVariables, principal: Principal): Any?
}
