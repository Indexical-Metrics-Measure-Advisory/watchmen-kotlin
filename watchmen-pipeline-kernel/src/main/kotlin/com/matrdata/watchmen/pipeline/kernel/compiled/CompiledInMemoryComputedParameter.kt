package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.ComputedParameter
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

/**
 * compiled in-memory computed parameter
 */
class CompiledInMemoryComputedParameter constructor(
	private val parameter: ComputedParameter
) : CompiledInMemoryParameter<ComputedParameter> {
	override fun getParameter(): ComputedParameter {
		return this.parameter
	}

	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}