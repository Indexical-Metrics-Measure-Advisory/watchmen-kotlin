package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageParameter
import com.matrdata.watchmen.model.common.ComputedParameter
import com.matrdata.watchmen.model.common.ConstantParameter
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.model.common.TopicFactorParameter

sealed interface InStorageParameterCompiler<P : Parameter> : Compiler<CompiledInStorageParameter<P>> {
	companion object {
		fun of(parameter: Parameter): InStorageParameterCompiler<out Parameter> {
			return when (parameter) {
				is TopicFactorParameter -> InStorageTopicFactorParameterCompiler.of(parameter)
				is ConstantParameter -> InStorageConstantParameterCompiler.of(parameter)
				is ComputedParameter -> InStorageComputedParameterCompiler.of(parameter)
			}
		}
	}
}

class PreparedInStorageParameterCompiler(
	private val parameter: Parameter, private val principal: Principal
) : PreparedCompiler<CompiledInStorageParameter<out Parameter>> {
	override fun compile(): CompiledInStorageParameter<out Parameter> {
		return InStorageParameterCompiler.of(this.parameter).compileBy(this.principal)
	}
}
