package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.common.ComputedParameter

/**
 * compiled in-memory computed parameter
 */
class CompiledInMemoryComputedParameter constructor(
	val parameter: ComputedParameter
) : CompiledInMemoryParameter<ComputedParameter> {
	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}