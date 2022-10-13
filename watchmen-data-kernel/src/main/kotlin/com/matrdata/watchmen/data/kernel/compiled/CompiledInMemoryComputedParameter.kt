package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RuntimeVariables
import com.matrdata.watchmen.model.admin.FactorType
import com.matrdata.watchmen.model.common.ComputedParameter

/**
 * compiled in-memory computed parameter
 */
class CompiledInMemoryComputedParameter constructor(
	val parameter: ComputedParameter,
	override val possibleTypes: List<FactorType>,
	val askValue: (RuntimeVariables, Principal) -> Any?,
	override val dependedDefs: List<Any>
) : CompiledInMemoryParameter<ComputedParameter> {
	override fun value(variables: RuntimeVariables, principal: Principal): Any? {
		// use compiled function to retrieve value
		return this.askValue(variables, principal)
	}
}