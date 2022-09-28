package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryParameter
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryTopicFactorParameter
import com.matrdata.watchmen.model.common.TopicFactorParameter

/**
 * in-memory topic factor parameter compiler
 */
class InMemoryTopicFactorParameterCompiler private constructor(private val parameter: TopicFactorParameter) :
	InMemoryParameterCompiler<TopicFactorParameter>, Compiler<CompiledInMemoryParameter<TopicFactorParameter>> {
	companion object {
		fun of(parameter: TopicFactorParameter): InMemoryTopicFactorParameterCompiler {
			return InMemoryTopicFactorParameterCompiler(parameter)
		}
	}

	override fun compileBy(principal: Principal): CompiledInMemoryTopicFactorParameter {
		return CompiledInMemoryTopicFactorParameter(parameter = this.parameter)
	}
}
