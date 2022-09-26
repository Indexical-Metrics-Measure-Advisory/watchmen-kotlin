package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

sealed interface CompiledInMemoryParameter<P : Parameter> {
	fun getParameter(): P?
	fun value(variables: PipelineVariables, principal: Principal): Any?
}
