package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.FactorType
import com.matrdata.watchmen.model.common.ConstantParameter
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInMemoryConstantParameter
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInMemoryParameter
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

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
		return CompiledInMemoryConstantParameter(
			parameter = this.parameter,
			// TODO not yet implemented
			possibleTypes = listOf(FactorType.TEXT),
			askValue = { v: PipelineVariables, _ -> null },
			dependedDefs = listOf()
		)
	}
}
