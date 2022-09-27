package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageParameter
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageTopicFactorParameter
import com.matrdata.watchmen.model.common.TopicFactorParameter

/**
 * in-storage topic factor parameter compiler
 */
class InStorageTopicFactorParameterCompiler private constructor(private val parameter: TopicFactorParameter) :
	InStorageParameterCompiler<TopicFactorParameter>, Compiler<CompiledInStorageParameter<TopicFactorParameter>> {
	companion object {
		fun of(parameter: TopicFactorParameter): InStorageTopicFactorParameterCompiler {
			return InStorageTopicFactorParameterCompiler(parameter)
		}
	}

	override fun compileBy(principal: Principal): CompiledInStorageTopicFactorParameter {
		return CompiledInStorageTopicFactorParameter(parameter = this.parameter)
	}
}
