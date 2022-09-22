package com.matrdata.watchmen.pipeline.kernel.compile.parameter

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.TopicFactorParameter
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables
import com.matrdata.watchmen.pipeline.kernel.compile.Compiler

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

/**
 * compiled in-memory topic factor parameter
 */
class CompiledInMemoryTopicFactorParameter constructor(
	private val parameter: TopicFactorParameter
) : CompiledInMemoryParameter<TopicFactorParameter> {
	override fun getParameter(): TopicFactorParameter {
		return this.parameter
	}

	override fun value(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}