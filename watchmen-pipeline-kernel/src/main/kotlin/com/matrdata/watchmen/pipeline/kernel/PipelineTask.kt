package com.matrdata.watchmen.pipeline.kernel

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.common.PipelineTriggerTraceId
import com.matrdata.watchmen.model.common.TopicDataId
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipeline
import com.matrdata.watchmen.pipeline.kernel.compiler.use

class PipelineTask(
	private val pipeline: Pipeline,
	private val previousData: Map<String, Any>?, private val currentData: Map<String, Any>?,
	private val principal: Principal,
	private val traceId: PipelineTriggerTraceId, private val dataId: TopicDataId
) {
	companion object {
		const val UNKNOWN_SYNONYM_DATA_ID: TopicDataId = -1
	}

	fun run(storages: TopicStorages, handleMonitorLog: PipelineMonitorLogHandle): List<PipelineTask> {
		return this.askCompiledPipeline()
			.runnable()
			.use(this.principal)
			.use(storages)
			.use(handleMonitorLog)
			.traceBy(this.traceId)
			.dataId(this.dataId)
			.run(previous = this.previousData, current = this.currentData)
	}

	private fun askCompiledPipelineFromCache(): CompiledPipeline? {
		TODO("Not yet implemented")
	}

	private fun putCompiledPipelineIntoCache(compiled: CompiledPipeline) {
		TODO("Not yet implemented")
	}

	private fun askCompiledPipeline(): CompiledPipeline {
		return this.askCompiledPipelineFromCache()
			?: this.pipeline.use(this.principal).compile().also { this.putCompiledPipelineIntoCache(it) }
	}
}
