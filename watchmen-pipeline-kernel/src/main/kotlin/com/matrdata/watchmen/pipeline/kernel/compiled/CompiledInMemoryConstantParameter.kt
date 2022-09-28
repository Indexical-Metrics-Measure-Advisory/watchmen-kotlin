package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.FactorType
import com.matrdata.watchmen.model.common.ConstantParameter
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

/**
 * compiled in-memory constant parameter
 */
class CompiledInMemoryConstantParameter constructor(
	val parameter: ConstantParameter,
	override val possibleTypes: List<FactorType>,
	val askValue: (PipelineVariables, Principal) -> Any?,
	override val dependedDefs: List<Any>
) : CompiledInMemoryParameter<ConstantParameter> {
	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		// use compiled function to retrieve value
		return this.askValue(variables, principal)
	}
}