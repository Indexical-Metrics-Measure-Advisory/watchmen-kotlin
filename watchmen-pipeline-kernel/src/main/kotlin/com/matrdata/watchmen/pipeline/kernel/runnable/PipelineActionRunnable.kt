package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.runtime.monitor.UnitMonitorLog
import com.matrdata.watchmen.pipeline.kernel.PipelineKernelException
import com.matrdata.watchmen.pipeline.kernel.TopicStorages
import com.matrdata.watchmen.pipeline.kernel.compiled.*
import com.matrdata.watchmen.utils.throwIfNull

class PipelineActionRunnable<out T : PipelineActionType, out A : PipelineAction<out T>, CA : CompiledAction<out T, out A>>(
	internal val pipeline: CompiledPipeline,
	internal val stage: CompiledStage,
	internal val unit: CompiledUnit,
	internal val compiled: CA,
	internal val principal: Principal,
	internal val storages: TopicStorages,
	internal val createPipelineTask: CreatePipelineTask,
	internal val log: UnitMonitorLog,
	internal val variables: PipelineVariables
) {
	fun run(): Boolean {
		// create wrapper and run
		@Suppress("UNCHECKED_CAST")
		return when (this.compiled) {
			is CompiledAlarmAction -> AlarmActionRunnable(this as PipelineActionRunnable<SystemActionType, AlarmAction, CompiledAlarmAction>).run()
			is CompiledCopyToMemoryAction -> CopyToMemoryActionRunnable(this as PipelineActionRunnable<SystemActionType, CopyToMemoryAction, CompiledCopyToMemoryAction>).run()
			is CompiledWriteToExternalAction -> WriteToExternalActionRunnable(this as PipelineActionRunnable<SystemActionType, WriteToExternalAction, CompiledWriteToExternalAction>).run()
			is CompiledReadRowAction -> ReadRowActionRunnable(this as PipelineActionRunnable<ReadTopicActionType, ReadRowAction, CompiledReadRowAction>).run()
			is CompiledReadRowsAction -> ReadRowsActionRunnable(this as PipelineActionRunnable<ReadTopicActionType, ReadRowsAction, CompiledReadRowsAction>).run()
			is CompiledExistsAction -> ExistsActionRunnable(this as PipelineActionRunnable<ReadTopicActionType, ExistsAction, CompiledExistsAction>).run()
			is CompiledReadFactorAction -> ReadFactorActionRunnable(this as PipelineActionRunnable<ReadTopicActionType, ReadFactorAction, CompiledReadFactorAction>).run()
			is CompiledReadFactorsAction -> ReadFactorsActionRunnable(this as PipelineActionRunnable<ReadTopicActionType, ReadFactorsAction, CompiledReadFactorsAction>).run()
			is CompiledInsertRowAction -> InsertRowActionRunnable(this as PipelineActionRunnable<WriteTopicActionType, InsertRowAction, CompiledInsertRowAction>).run()
			is CompiledMergeRowAction -> MergeRowActionRunnable(this as PipelineActionRunnable<WriteTopicActionType, MergeRowAction, CompiledMergeRowAction>).run()
			is CompiledInsertOrMergeRowAction -> InsertOrMergeRowActionRunnable(this as PipelineActionRunnable<WriteTopicActionType, InsertOrMergeRowAction, CompiledInsertOrMergeRowAction>).run()
			is CompiledWriteFactorAction -> WriteFactorActionRunnable(this as PipelineActionRunnable<WriteTopicActionType, WriteFactorAction, CompiledWriteFactorAction>).run()
			is CompiledDeleteRowAction -> DeleteRowActionRunnable(this as PipelineActionRunnable<DeleteTopicActionType, DeleteRowAction, CompiledDeleteRowAction>).run()
			is CompiledDeleteRowsAction -> DeleteRowsActionRunnable(this as PipelineActionRunnable<DeleteTopicActionType, DeleteRowsAction, CompiledDeleteRowsAction>).run()
			else -> throw PipelineKernelException("Action[${this.compiled}] is not supported.")
		}
	}
}

/**
 * wrapper of pipeline action runnable, the actual runnable
 */
sealed class PipelineActionRunnableWrapper<T : PipelineActionType, A : PipelineAction<T>, CA : CompiledAction<T, A>>(
	private val wrapped: PipelineActionRunnable<T, A, CA>
) {
	internal val pipeline: CompiledPipeline get() = wrapped.pipeline
	internal val stage: CompiledStage get() = wrapped.stage
	internal val unit: CompiledUnit get() = wrapped.unit
	internal val compiled: CA get() = wrapped.compiled
	internal val principal: Principal get() = wrapped.principal
	internal val storages: TopicStorages get() = wrapped.storages
	internal val createPipelineTask: CreatePipelineTask get() = wrapped.createPipelineTask
	internal val log: UnitMonitorLog get() = wrapped.log
	internal val variables: PipelineVariables get() = wrapped.variables
}

class PipelineActionRunnableCommand(
	private val pipeline: CompiledPipeline,
	private val stage: CompiledStage,
	private val unit: CompiledUnit,
	private val action: CompiledAction<out PipelineActionType, out PipelineAction<out PipelineActionType>>
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