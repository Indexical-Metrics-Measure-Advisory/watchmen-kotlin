package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RunnableContext
import com.matrdata.watchmen.model.common.ComputedParameter

/**
 * compiled in-memory computed parameter
 */
class CompiledInMemoryComputedParameter constructor(
	val parameter: ComputedParameter
) : CompiledInMemoryParameter<ComputedParameter> {
	override fun value(variables: RunnableContext, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}