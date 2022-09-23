package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.ConstantParameter
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables

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