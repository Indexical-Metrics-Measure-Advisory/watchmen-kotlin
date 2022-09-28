package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.ComputedParameter
import com.matrdata.watchmen.model.common.ConstantParameter
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.model.common.TopicFactorParameter
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInMemoryParameter
import com.matrdata.watchmen.utils.throwIfNull

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
	private val parameter: Parameter
) : PreparedCompiler<CompiledInMemoryParameter<out Parameter>> {
	private var principal: Principal? = null

	fun use(principal: Principal) = apply { this.principal = principal }

	override fun compile(): CompiledInMemoryParameter<out Parameter> {
		this.principal.throwIfNull { "Principal cannot be null on in-memory parameter compiling." }

		return InMemoryParameterCompiler.of(this.parameter).compileBy(this.principal!!)
	}
}

fun Parameter.inMemory(): PreparedInMemoryParameterCompiler {
	return PreparedInMemoryParameterCompiler(parameter = this)
}