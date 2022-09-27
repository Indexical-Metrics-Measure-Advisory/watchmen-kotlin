package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageComputedParameter
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageParameter
import com.matrdata.watchmen.model.common.ComputedParameter

/**
 * in-storage computed parameter compiler
 */
class InStorageComputedParameterCompiler private constructor(private val parameter: ComputedParameter) :
	InStorageParameterCompiler<ComputedParameter>, Compiler<CompiledInStorageParameter<ComputedParameter>> {
	companion object {
		fun of(parameter: ComputedParameter): InStorageComputedParameterCompiler {
			return InStorageComputedParameterCompiler(parameter)
		}
	}

	override fun compileBy(principal: Principal): CompiledInStorageComputedParameter {
		return CompiledInStorageComputedParameter(parameter = this.parameter)
	}
}
