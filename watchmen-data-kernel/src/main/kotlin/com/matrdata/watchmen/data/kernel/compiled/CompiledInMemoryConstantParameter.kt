package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.common.ConstantParameter

/**
 * compiled in-memory constant parameter
 */
class CompiledInMemoryConstantParameter constructor(
	private val parameter: ConstantParameter
) : CompiledInMemoryParameter<ConstantParameter> {
	override fun getParameter(): ConstantParameter {
		return this.parameter
	}

	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}