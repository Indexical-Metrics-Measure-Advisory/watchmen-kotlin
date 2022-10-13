package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RuntimeVariables
import com.matrdata.watchmen.model.admin.FactorType
import com.matrdata.watchmen.model.common.ConstantParameter

/**
 * compiled in-memory constant parameter
 */
class CompiledInMemoryConstantParameter constructor(
	val parameter: ConstantParameter,
	override val possibleTypes: List<FactorType>,
	val askValue: (RuntimeVariables, Principal) -> Any?,
	override val dependedDefs: List<Any>
) : CompiledInMemoryParameter<ConstantParameter> {
	override fun value(variables: RuntimeVariables, principal: Principal): Any? {
		// use compiled function to retrieve value
		return this.askValue(variables, principal)
	}
}