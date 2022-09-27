package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Parameter

class ParameterCompilerSwitcher(private val parameter: Parameter, private val principal: Principal) {
	fun inMemory(): PreparedInMemoryParameterCompiler {
		return PreparedInMemoryParameterCompiler(parameter = this.parameter, principal = this.principal)
	}

	fun inStorage(): PreparedInStorageParameterCompiler {
		return PreparedInStorageParameterCompiler(parameter = this.parameter, principal = this.principal)
	}
}

fun Parameter.use(principal: Principal): ParameterCompilerSwitcher {
	return ParameterCompilerSwitcher(parameter = this, principal = principal)
}
