package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.ComputedParameter
import com.matrdata.watchmen.model.common.ConstantParameter
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.model.common.TopicFactorParameter
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryParameter

sealed interface InMemoryParameterCompiler<P : Parameter> : Compiler<CompiledInMemoryParameter<P>> {
	companion object {
		fun of(parameter: Parameter): InMemoryParameterCompiler<out Parameter> {
			return when (parameter) {
				is TopicFactorParameter -> InMemoryTopicFactorParameterCompiler.of(parameter)
				is ConstantParameter -> InMemoryConstantParameterCompiler.of(parameter)
				is ComputedParameter -> InMemoryComputedParameterCompiler.of(parameter)
			}
		}
	}
}

class PreparedInMemoryParameterCompiler(
	private val parameter: Parameter, private val principal: Principal
) : PreparedCompiler<CompiledInMemoryParameter<out Parameter>> {
	override fun compile(): CompiledInMemoryParameter<out Parameter> {
		return InMemoryParameterCompiler.of(this.parameter).compileBy(this.principal)
	}
}
