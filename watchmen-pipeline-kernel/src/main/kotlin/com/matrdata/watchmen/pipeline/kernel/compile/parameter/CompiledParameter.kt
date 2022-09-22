package com.matrdata.watchmen.pipeline.kernel.compile.parameter

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Parameter

class ParameterCompilerSwitcher(private val parameter: Parameter, private val principal: Principal) {
	fun inMemory(): PreparedInMemoryParameterCompiler {
		return PreparedInMemoryParameterCompiler(parameter = this.parameter, principal = this.principal)
	}

	// TODO not yet implemented, in-storage condition compiler
//	fun inStorage(): PrepareInStorageConditionCompiler {
//		return PrepareInStorageConditionCompiler(condition = this.condition, principal = this.principal)
//	}
}

fun Parameter.use(principal: Principal): ParameterCompilerSwitcher {
	return ParameterCompilerSwitcher(parameter = this, principal = principal)
}
