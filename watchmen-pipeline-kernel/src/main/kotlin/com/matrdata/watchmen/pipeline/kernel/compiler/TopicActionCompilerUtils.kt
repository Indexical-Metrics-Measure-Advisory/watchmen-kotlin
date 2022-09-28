package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageCondition
import com.matrdata.watchmen.data.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.data.kernel.compiler.inStorage
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.FindBy
import com.matrdata.watchmen.model.admin.MappingFactor
import com.matrdata.watchmen.model.admin.MappingRow
import com.matrdata.watchmen.model.admin.PipelineActionType
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.pipeline.kernel.PipelineKernelException
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInStorageMappingFactor
import com.matrdata.watchmen.utils.throwIfNull2
import com.matrdata.watchmen.utils.throwIfNullOrEmpty2

internal fun FindBy<out PipelineActionType>.compileFindBy(
	topicSchema: TopicSchema, variables: CompiledVariables, principal: Principal
): CompiledInStorageCondition<out Condition> {
	// TODO use topic schema and variables on filter by compiling
	val actionId = this.actionId
	return this.by.throwIfNull2 {
		PipelineKernelException("By of action[id=$actionId] cannot be null.")
	}.inStorage().use(principal).compile()
}

internal fun MappingFactor.compile(
	topicSchema: TopicSchema, variables: CompiledVariables, principal: Principal
): CompiledInStorageMappingFactor {
	// TODO use topic schema and variables on mapping factor compiling
	return InStorageMappingFactorCompiler.of(this).compileBy(principal)
}

internal fun MappingRow.compileMappingFactors(
	topicSchema: TopicSchema, variables: CompiledVariables, principal: Principal
): List<CompiledInStorageMappingFactor> {
	return this.mapping.throwIfNullOrEmpty2 {
		PipelineKernelException("Mapping of action[id=${actionId}] cannot be null or empty.")
	}.map { mappingFactor ->
		mappingFactor.compile(topicSchema, variables, principal)
	}
}
