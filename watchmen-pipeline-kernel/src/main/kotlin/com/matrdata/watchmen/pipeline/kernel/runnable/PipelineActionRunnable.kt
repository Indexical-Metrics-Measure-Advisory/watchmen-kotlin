package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.AlarmAction
import com.matrdata.watchmen.model.admin.PipelineAction
import com.matrdata.watchmen.model.admin.PipelineActionType
import com.matrdata.watchmen.model.admin.SystemActionType
import com.matrdata.watchmen.model.runtime.monitor.UnitMonitorLog
import com.matrdata.watchmen.pipeline.kernel.TopicStorages
import com.matrdata.watchmen.pipeline.kernel.compiled.*
import com.matrdata.watchmen.utils.throwIfNull

class PipelineActionRunnable<out T : PipelineActionType, out A : PipelineAction<out T>, CA : CompiledPipelineAction<out T, out A>>(
	internal val pipeline: CompiledPipeline,
	internal val stage: CompiledPipelineStage,
	internal val unit: CompiledPipelineUnit,
	internal val compiled: CA,
	internal val principal: Principal,
	internal val storages: TopicStorages,
	internal val createPipelineTask: CreatePipelineTask,
	internal val log: UnitMonitorLog,
	internal val variables: PipelineVariables
) {
	fun run(): Boolean {
		@Suppress("UNCHECKED_CAST")
		return when (this.compiled) {
			is CompiledAlarmAction -> AlarmActionRunnable(this as PipelineActionRunnable<SystemActionType, AlarmAction, CompiledAlarmAction>)
			else -> TODO("Not yet implemented")
		}.run()
	}
}

abstract class SpecificPipelineActionRunnable<T : PipelineActionType, A : PipelineAction<T>, CA : CompiledPipelineAction<T, A>>(
	private val wrapped: PipelineActionRunnable<T, A, CA>
) {
	internal val pipeline: CompiledPipeline get() = wrapped.pipeline
	internal val stage: CompiledPipelineStage get() = wrapped.stage
	internal val unit: CompiledPipelineUnit get() = wrapped.unit
	internal val compiled: CA get() = wrapped.compiled
	internal val principal: Principal get() = wrapped.principal
	internal val storages: TopicStorages get() = wrapped.storages
	internal val createPipelineTask: CreatePipelineTask get() = wrapped.createPipelineTask
	internal val log: UnitMonitorLog get() = wrapped.log
	internal val variables: PipelineVariables get() = wrapped.variables
}

class PipelineActionRunnableCommand(
	private val pipeline: CompiledPipeline,
	private val stage: CompiledPipelineStage,
	private val unit: CompiledPipelineUnit,
	private val action: CompiledPipelineAction<out PipelineActionType, out PipelineAction<out PipelineActionType>>
) {
	private var log: UnitMonitorLog? = null
	private var principal: Principal? = null
	private var storages: TopicStorages? = null
	private var createPipelineTask: CreatePipelineTask? = null

	fun inherit(log: UnitMonitorLog) = apply { this.log = log }
	fun use(principal: Principal) = apply { this.principal = principal }
	fun use(storages: TopicStorages) = apply { this.storages = storages }
	fun use(createPipelineTask: CreatePipelineTask) = apply { this.createPipelineTask = createPipelineTask }

	fun run(variables: PipelineVariables): Boolean {
		return PipelineActionRunnable(
			pipeline = this.pipeline,
			stage = this.stage,
			unit = this.unit,
			compiled = this.action,
			principal = this.principal.throwIfNull { "Principal cannot be null when run action." },
			storages = this.storages.throwIfNull { "Topic storages cannot be null when run action." },
			createPipelineTask = this.createPipelineTask.throwIfNull { "Pipeline task creator cannot be null when run action." },
			log = this.log.throwIfNull { "Unit monitor log cannot be null when run action." },
			variables = variables
		).run()
	}
}