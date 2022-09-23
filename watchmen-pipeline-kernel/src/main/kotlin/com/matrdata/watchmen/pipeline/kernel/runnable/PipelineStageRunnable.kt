package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.runtime.monitor.PipelineMonitorLog
import com.matrdata.watchmen.pipeline.kernel.TopicStorages
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineStage
import com.matrdata.watchmen.utils.throwIfNull

class PipelineStageRunnable(
	private val pipeline: CompiledPipeline,
	private val compiled: CompiledPipelineStage,
	private val principal: Principal,
	private val storages: TopicStorages,
	private val createPipelineTask: CreatePipelineTask,
	private val variables: PipelineVariables
) {
	fun run(): Boolean {
		TODO("Not yet implemented")
	}
}

class PipelineStageRunnableBuilder(
	private val pipeline: CompiledPipeline, private val stage: CompiledPipelineStage
) {
	private var log: PipelineMonitorLog? = null
	private var principal: Principal? = null
	private var storages: TopicStorages? = null
	private var createPipelineTask: CreatePipelineTask? = null

	fun inherit(log: PipelineMonitorLog) = apply { this.log = log }
	fun use(principal: Principal) = apply { this.principal = principal }
	fun use(storages: TopicStorages) = apply { this.storages = storages }
	fun use(createPipelineTask: CreatePipelineTask) = apply { this.createPipelineTask = createPipelineTask }

	fun run(variables: PipelineVariables): Boolean {
		return PipelineStageRunnable(
			pipeline = this.pipeline,
			compiled = this.stage,
			principal = this.principal.throwIfNull { "Principal cannot be null when run stage." },
			storages = this.storages.throwIfNull { "Topic storages cannot be null when run stage." },
			createPipelineTask = this.createPipelineTask.throwIfNull { "Pipeline task creator cannot be null when run stage." },
			variables = variables
		).run()
	}
}