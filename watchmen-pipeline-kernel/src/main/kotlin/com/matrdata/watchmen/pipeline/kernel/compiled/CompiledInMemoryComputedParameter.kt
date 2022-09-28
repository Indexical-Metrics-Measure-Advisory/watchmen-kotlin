package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.FactorType
import com.matrdata.watchmen.model.common.ComputedParameter
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

/**
 * compiled in-memory computed parameter
 */
class CompiledInMemoryComputedParameter constructor(
	val parameter: ComputedParameter,
	override val possibleTypes: List<FactorType>,
	val askValue: (PipelineVariables, Principal) -> Any?,
	override val dependedDefs: List<Any>
) : CompiledInMemoryParameter<ComputedParameter> {
	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		// use compiled function to retrieve value
		return this.askValue(variables, principal)
	}
}