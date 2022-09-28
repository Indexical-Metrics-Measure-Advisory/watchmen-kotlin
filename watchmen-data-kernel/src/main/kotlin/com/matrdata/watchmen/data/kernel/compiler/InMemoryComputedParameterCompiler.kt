package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryComputedParameter
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryParameter
import com.matrdata.watchmen.model.common.ComputedParameter

/**
 * in-memory computed parameter compiler
 */
class InMemoryComputedParameterCompiler private constructor(private val parameter: ComputedParameter) :
	InMemoryParameterCompiler<ComputedParameter>, Compiler<CompiledInMemoryParameter<ComputedParameter>> {
	companion object {
		fun of(parameter: ComputedParameter): InMemoryComputedParameterCompiler {
			return InMemoryComputedParameterCompiler(parameter)
		}
	}

	override fun compileBy(principal: Principal): CompiledInMemoryComputedParameter {
		return CompiledInMemoryComputedParameter(parameter = this.parameter)
	}
}
