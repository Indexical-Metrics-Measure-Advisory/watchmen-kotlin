package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.common.ConstantParameter

/**
 * compiled in-storage constant parameter
 */
class CompiledInStorageConstantParameter constructor(
	val parameter: ConstantParameter
) : CompiledInStorageParameter<ConstantParameter> {
	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}