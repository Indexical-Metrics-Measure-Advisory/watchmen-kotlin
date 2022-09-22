package com.matrdata.watchmen.pipeline.kernel.compile.parameter

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.ComputedParameter
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables
import com.matrdata.watchmen.pipeline.kernel.compile.Compiler

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

/**
 * compiled in-memory computed parameter
 */
class CompiledInMemoryComputedParameter constructor(
	private val parameter: ComputedParameter
) : CompiledInMemoryParameter<ComputedParameter> {
	override fun getParameter(): ComputedParameter {
		return this.parameter
	}

	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}