package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageConstantParameter
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageParameter
import com.matrdata.watchmen.model.common.ConstantParameter

/**
 * in-storage constant parameter compiler
 */
class InStorageConstantParameterCompiler private constructor(private val parameter: ConstantParameter) :
	InStorageParameterCompiler<ConstantParameter>, Compiler<CompiledInStorageParameter<ConstantParameter>> {
	companion object {
		fun of(parameter: ConstantParameter): InStorageConstantParameterCompiler {
			return InStorageConstantParameterCompiler(parameter)
		}
	}

	override fun compileBy(principal: Principal): CompiledInStorageConstantParameter {
		return CompiledInStorageConstantParameter(parameter = this.parameter)
	}
}
