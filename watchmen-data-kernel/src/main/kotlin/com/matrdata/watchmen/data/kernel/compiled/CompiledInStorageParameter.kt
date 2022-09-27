package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.common.Parameter

sealed interface CompiledInStorageParameter<P : Parameter> {
	fun getParameter(): P?
	fun value(variables: PipelineVariables, principal: Principal): Any?
}
