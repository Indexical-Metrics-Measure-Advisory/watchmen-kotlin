package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.FactorType
import com.matrdata.watchmen.model.common.ComputedParameter
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInMemoryComputedParameter
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInMemoryParameter
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

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
		return CompiledInMemoryComputedParameter(
			parameter = this.parameter,
			// TODO not yet implemented
			possibleTypes = listOf(FactorType.TEXT),
			askValue = { v: PipelineVariables, _ -> null },
			dependedDefs = listOf()
		)
	}
}
