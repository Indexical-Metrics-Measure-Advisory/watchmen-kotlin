package com.matrdata.watchmen.pipeline.kernel.compile.parameter

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.ConstantParameter
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables
import com.matrdata.watchmen.pipeline.kernel.compile.Compiler

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

/**
 * compiled in-memory constant parameter
 */
class CompiledInMemoryConstantParameter constructor(
	private val parameter: ConstantParameter
) : CompiledInMemoryParameter<ConstantParameter> {
	override fun getParameter(): ConstantParameter {
		return this.parameter
	}

	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}