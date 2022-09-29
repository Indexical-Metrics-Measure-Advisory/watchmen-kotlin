package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.utils.askFactorById
import com.matrdata.watchmen.data.kernel.utils.askTopicSchemaById
import com.matrdata.watchmen.model.common.TopicFactorParameter
import com.matrdata.watchmen.pipeline.kernel.PipelineKernelException
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInMemoryTopicFactorParameter
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.utils.throwIfBlank2
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
		val factorName = factor.name.throwIfBlank2 {
			PipelineKernelException("Factor name of parameter[${parameter}] cannot be null or blank.")
		}
		val factorType = factor.type.throwIfNull2 {
			PipelineKernelException("Factor type of parameter[${parameter}] cannot be null.")
		}

		return CompiledInMemoryTopicFactorParameter(
			parameter = this.parameter,
			possibleTypes = listOf(factorType),
			askValue = { v: PipelineVariables, _ ->
				// get value from current trigger data by declared factor's name
				v.findFromCurrent(factorName)
			},
			dependedDefs = listOf(topicSchema.topic)
		)
	}
}
