package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.DataKernelException
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryTopicFactorParameter
import com.matrdata.watchmen.data.kernel.runnable.RuntimeVariables
import com.matrdata.watchmen.data.kernel.utils.askFactorById
import com.matrdata.watchmen.data.kernel.utils.askTopicSchemaById
import com.matrdata.watchmen.model.common.TopicFactorParameter
import com.matrdata.watchmen.utils.throwIfNull2

/**
 * in-memory topic factor parameter compiler
 */
class InMemoryTopicFactorParameterCompiler private constructor(private val parameter: TopicFactorParameter) :
	InMemoryParameterCompiler<TopicFactorParameter> {
	companion object {
		fun of(parameter: TopicFactorParameter): InMemoryTopicFactorParameterCompiler {
			return InMemoryTopicFactorParameterCompiler(parameter)
		}
	}

	override fun compileBy(principal: Principal): CompiledInMemoryTopicFactorParameter {
		val topicSchema = askTopicSchemaById(this.parameter.topicId, principal) { "parameter[${parameter}]" }
		val factor = askFactorById(topicSchema, this.parameter.factorId) { "parameter[${parameter}]" }
		val factorType = factor.type.throwIfNull2 {
			DataKernelException("Factor type of parameter[${parameter}] cannot be null.")
		}

		return CompiledInMemoryTopicFactorParameter(
			parameter = this.parameter,
			possibleTypes = listOf(factorType),
			askValue = { v: RuntimeVariables, _ ->
				// get value from current trigger data by declared factor's name
				v.findByFactor(factor)
			},
			dependedDefs = listOf(topicSchema.topic)
		)
	}
}
