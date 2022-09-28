package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryConstantParameter
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryParameter
import com.matrdata.watchmen.model.common.ConstantParameter

/**
 * in-memory constant parameter compiler
 */
class InMemoryConstantParameterCompiler private constructor(private val parameter: ConstantParameter) :
	InMemoryParameterCompiler<ConstantParameter>, Compiler<CompiledInMemoryParameter<ConstantParameter>> {
	companion object {
		fun of(parameter: ConstantParameter): InMemoryConstantParameterCompiler {
			return InMemoryConstantParameterCompiler(parameter)
		}
	}

	override fun compileBy(principal: Principal): CompiledInMemoryConstantParameter {
		return CompiledInMemoryConstantParameter(parameter = this.parameter)
	}
}
