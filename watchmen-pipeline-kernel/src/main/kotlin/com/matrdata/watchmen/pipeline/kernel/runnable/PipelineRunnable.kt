package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.common.PipelineTriggerTraceId
import com.matrdata.watchmen.model.common.TopicDataId
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.model.runtime.monitor.PipelineMonitorLog
import com.matrdata.watchmen.pipeline.kernel.PipelineMonitorLogHandle
import com.matrdata.watchmen.pipeline.kernel.PipelineTask
import com.matrdata.watchmen.pipeline.kernel.TopicStorages
import com.matrdata.watchmen.pipeline.kernel.askIsAsyncHandlePipelineMonitorLog
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledStage
import com.matrdata.watchmen.pipeline.kernel.compiled.PipelineTrigger
import com.matrdata.watchmen.pipeline.kernel.utils.askNextIdAsStr
import com.matrdata.watchmen.pipeline.kernel.utils.askTopicById
import com.matrdata.watchmen.utils.*
import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import java.time.LocalDateTime

class PipelineRunnable(
	private val compiled: CompiledPipeline,
	private val principal: Principal,
	private val storages: TopicStorages,
	private val handleMonitorLog: PipelineMonitorLogHandle,
	private val traceId: PipelineTriggerTraceId,
	private val dataId: TopicDataId,
	private val previousData: Map<String, Any>?,
	private val currentData: Map<String, Any>?
) {
	private fun runStage(
		stage: CompiledStage,
		variables: PipelineVariables, log: PipelineMonitorLog, createPipelineTask: CreatePipelineTask
	): Boolean {
		return stage.runnable(this.compiled)
			.inherit(log)
			.use(this.principal)
			.use(storages)
			.use(createPipelineTask)
			.run(variables)
	}

	private fun runStages(
		variables: PipelineVariables, log: PipelineMonitorLog, createPipelineTask: CreatePipelineTask
	): Boolean {
		return this.compiled.stages.fold(initial = true) { should, stage: CompiledStage ->
			if (!should) {
				// ignore stage run when previous said should not
				false
			} else {
				this.runStage(stage, variables, log, createPipelineTask)
			}
		}
	}

	fun run(): List<PipelineTask> {
		// build pipeline variables
		val triggerTopic = this.compiled.pipeline.topicId.throwIfBlank {
			"Trigger topic id of pipeline[id=${compiled.pipeline.pipelineId}] cannot be blank."
		}.handTo { topicId ->
			askTopicById(topicId, principal) { "of pipeline[id=${compiled.pipeline.pipelineId}]" }
		}
		// create variables
		val variables = PipelineVariables(this.previousData, this.currentData, triggerTopic)
		// create monitor log
		val log = createLog()
		// create queue, accepts new tasks which created by this
		val taskQueue = PipelineTaskQueue()
		try {
			// test prerequisite
			this.compiled.prerequisiteTest(variables, this.principal)
				.doIfFalse {
					// prerequisite check returns false
					// no need to run pipeline, log status as done
					log.prerequisite = false
				}.doIfTrue {
					// prerequisite check returns true
					log.prerequisite = true
					// run stages
					runStages(variables, log) { schema: TopicSchema, trigger: PipelineTrigger ->
						taskQueue.append(schema, trigger, traceId, principal)
					}
						// stages run successfully
						.doIfTrue { log.status = MonitorLogStatus.DONE }
						// error occurred in stages running, log status on pipeline runtime log
						.doIfFalse { log.status = MonitorLogStatus.ERROR }
				}
		} catch (t: Throwable) {
			// unexpected throwable, log it on pipeline runtime log
			logger.error("Unexpected exception raised.", t)
			log.apply {
				status = MonitorLogStatus.ERROR
				error = t.stackTraceToString()
			}
		}

		log.apply {
			// log spent in milliseconds
			spentInMills = startTime?.spentInMs()?.toInt() ?: 0
		}.also {
			// trigger log pipeline
			this.handleMonitorLog(it, askIsAsyncHandlePipelineMonitorLog())
		}

		// return created pipelines
		return taskQueue.getTasks()
	}

	private fun createLog(): PipelineMonitorLog {
		return PipelineMonitorLog(
			// create uid of compiled monitor log
			uid = askNextIdAsStr(),
			traceId = this.traceId, dataId = dataId,
			pipelineId = this.compiled.pipeline.pipelineId!!, topicId = this.compiled.pipeline.topicId!!,
			oldValue = this.previousData.toJSON(),
			newValue = this.currentData.toJSON(),
			prerequisite = true,
			prerequisiteDefinedAs = this.compiled.prerequisiteDef,
			stages = mutableListOf(),
			status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null
		)
	}
}

class PipelineRunnableCommand(private val pipeline: CompiledPipeline) {
	private var principal: Principal? = null
	private var storages: TopicStorages? = null
	private var handleMonitorLog: PipelineMonitorLogHandle? = null
	private var traceId: PipelineTriggerTraceId? = null
	private var dataId: TopicDataId? = null

	fun use(principal: Principal) = apply { this.principal = principal }
	fun use(storages: TopicStorages) = apply { this.storages = storages }
	fun use(handleMonitorLog: PipelineMonitorLogHandle) = apply { this.handleMonitorLog = handleMonitorLog }
	fun traceBy(traceId: PipelineTriggerTraceId) = apply { this.traceId = traceId }
	fun dataId(dataId: TopicDataId) = apply { this.dataId = dataId }

	fun run(previous: Map<String, Any>?, current: Map<String, Any>?): List<PipelineTask> {
		return PipelineRunnable(
			compiled = this.pipeline,
			principal = this.principal.throwIfNull { "Principal cannot be null when run pipeline." },
			storages = this.storages.throwIfNull { "Topic storages cannot be null when run pipeline." },
			handleMonitorLog = this.handleMonitorLog.throwIfNull { "Run log monitor cannot be null when run pipeline." },
			traceId = this.traceId.throwIfBlank { "Trace ID cannot be null when run pipeline." },
			dataId = this.dataId.throwIfNull { "Trace ID cannot be null when run pipeline." },
			previousData = previous,
			currentData = current
		).run()
	}
}
