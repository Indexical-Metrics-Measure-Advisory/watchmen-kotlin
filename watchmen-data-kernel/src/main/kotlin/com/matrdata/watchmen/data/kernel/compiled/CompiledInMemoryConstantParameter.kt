package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RunnableContext
import com.matrdata.watchmen.model.common.ConstantParameter

/**
 * compiled in-memory constant parameter
 */
class CompiledInMemoryConstantParameter constructor(
	val parameter: ConstantParameter
) : CompiledInMemoryParameter<ConstantParameter> {
	override fun value(variables: RunnableContext, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}