package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RunnableContext
import com.matrdata.watchmen.model.common.ComputedParameter

/**
 * compiled in-storage computed parameter
 */
class CompiledInStorageComputedParameter constructor(
	val parameter: ComputedParameter
) : CompiledInStorageParameter<ComputedParameter> {
	override fun value(variables: RunnableContext, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}