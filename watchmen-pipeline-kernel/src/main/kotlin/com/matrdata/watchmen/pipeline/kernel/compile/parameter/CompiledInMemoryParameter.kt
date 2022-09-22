package com.matrdata.watchmen.pipeline.kernel.compile.parameter

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.*
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables
import com.matrdata.watchmen.pipeline.kernel.compile.Compiler
import com.matrdata.watchmen.pipeline.kernel.compile.PreparedCompiler

sealed interface CompiledInMemoryParameter<P : Parameter> {
	fun getParameter(): P
	fun value(variables: PipelineVariables, principal: Principal): Any?
}

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