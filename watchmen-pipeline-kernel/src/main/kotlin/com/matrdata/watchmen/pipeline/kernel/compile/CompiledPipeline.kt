package com.matrdata.watchmen.pipeline.kernel.compile

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineTriggerType
import com.matrdata.watchmen.model.common.PipelineTriggerTraceId
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.model.runtime.monitor.PipelineMonitorLog
import com.matrdata.watchmen.pipeline.kernel.*
import com.matrdata.watchmen.pipeline.kernel.compile.conditional.PrerequisiteTest
import com.matrdata.watchmen.pipeline.kernel.compile.conditional.use
import com.matrdata.watchmen.utils.*
import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import java.time.LocalDateTime

class PipelineCompiler private constructor(private val pipeline: Pipeline) : Compiler<CompiledPipeline> {
	companion object {
		fun of(pipeline: Pipeline): PipelineCompiler {
			return PipelineCompiler(pipeline)
		}
	}

	override fun compileBy(principal: Principal): CompiledPipeline {
		return CompiledPipeline(
			pipeline = this.pipeline,
			// generate definition json string
			prerequisiteDef = pipeline.toPrerequisiteDefJSON(),
			// compile prerequisite to test function
			prerequisiteTest = pipeline.use(principal).compile(),
			stages = with(pipeline.stages) {
				require(!this.isNullOrEmpty()) { "Stage not exists in pipeline[$pipeline]." }
				this.map { it.within(pipeline).use(principal).compile() }
			}
		)
	}
}

@Slf4j
class PipelineTaskQueue {
	private val tasks: MutableList<PipelineTask> = mutableListOf()

	private fun shouldRun(pipeline: Pipeline, triggerType: PipelineTriggerType): Boolean {
		return when {
			pipeline.enabled != true -> false

			triggerType == PipelineTriggerType.DELETE -> {
				pipeline.type == PipelineTriggerType.DELETE
			}

			triggerType == PipelineTriggerType.INSERT -> {
				pipeline.type == PipelineTriggerType.INSERT || pipeline.type == PipelineTriggerType.INSERT_OR_MERGE
			}

			triggerType == PipelineTriggerType.MERGE -> {
				pipeline.type == PipelineTriggerType.MERGE || pipeline.type == PipelineTriggerType.INSERT_OR_MERGE
			}

			triggerType == PipelineTriggerType.INSERT_OR_MERGE -> {
				pipeline.type == PipelineTriggerType.INSERT_OR_MERGE
			}

			else -> throw PipelineKernelException("Pipeline trigger type[$triggerType] is not supported.")
		}
	}

	fun append(
		schema: TopicSchema, trigger: PipelineTrigger, traceId: PipelineTriggerTraceId, principal: Principal
	): List<PipelineTask> {
		val topic = schema.topic
		val pipelines =
			askPipelineMetaService(principal).findByTopicId(topic.topicId!!).filter {
				this.shouldRun(it, trigger.triggerType)
			}

		if (pipelines.isEmpty()) {
			logger.warn("No pipeline needs to be triggered by topic[id=${topic.topicId}, name=${topic.name}].")
			return listOf()
		}

		return pipelines.map { pipeline ->
			PipelineTaskBuilder.of(pipeline)
				.data(trigger.previous, trigger.current)
				.traceBy(traceId)
				.dataId(trigger.dataId)
				.by(principal)
				.also { task -> this.tasks.add(task) }
		}
	}

	fun getTasks(): List<PipelineTask> {
		return this.tasks
	}
}

@Slf4j
class CompiledPipelineRunnable(
	private val compiled: CompiledPipeline,
	private val principal: Principal,
	private val storages: TopicStorages,
	private val handleMonitorLog: PipelineMonitorLogHandle,
	private val traceId: PipelineTraceId,
	private val dataId: TopicDataId,
	private val previousData: Map<String, Any>?,
	private val currentData: Map<String, Any>?
) {
	private fun runStages(): Boolean {
//		this.compiled.stages.{ acc, compiledPipelineStage -> }
		TODO("Not yet implemented")
	}

	fun run(): List<PipelineTask> {
		// build pipeline variables
		val triggerTopicId =
			this.compiled.pipeline.topicId.throwIfBlank {
				"Trigger topic id of pipeline[id=${compiled.pipeline.pipelineId}] cannot be blank."
			}
		val triggerTopic =
			askTopicMetaService(principal).findById(triggerTopicId).throwIfNull {
				"Topic[id=$triggerTopicId] not found."
			}
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
					log.status = MonitorLogStatus.DONE
				}.doIfTrue {
					// prerequisite check returns true
					log.prerequisite = true
					// run stages
					runStages()
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
			spentInMills = log.startTime?.spentInMs()?.toInt() ?: 0
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
			uid = askSnowflakeGenerator().nextIdAsStr(),
			traceId = this.traceId, dataId = dataId,
			pipelineId = this.compiled.pipeline.pipelineId!!, topicId = this.compiled.pipeline.topicId!!,
			status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null,
			oldValue = this.previousData.toJSON(),
			newValue = this.currentData.toJSON(),
			prerequisite = true,
			prerequisiteDefinedAs = this.compiled.prerequisiteDef,
			stages = mutableListOf()
		)
	}
}

class CompiledPipelineRunnableBuilder(private val pipeline: CompiledPipeline) {
	private var principal: Principal? = null
	private var storages: TopicStorages? = null
	private var handleMonitorLog: PipelineMonitorLogHandle? = null
	private var traceId: PipelineTraceId? = null
	private var dataId: TopicDataId? = null

	fun use(principal: Principal): CompiledPipelineRunnableBuilder {
		this.principal = principal
		return this
	}

	fun use(storages: TopicStorages): CompiledPipelineRunnableBuilder {
		this.storages = storages
		return this
	}

	fun use(handleMonitorLog: PipelineMonitorLogHandle): CompiledPipelineRunnableBuilder {
		this.handleMonitorLog = handleMonitorLog
		return this
	}

	fun traceBy(traceId: PipelineTraceId): CompiledPipelineRunnableBuilder {
		this.traceId = traceId
		return this
	}

	fun dataId(dataId: TopicDataId): CompiledPipelineRunnableBuilder {
		this.dataId = dataId
		return this
	}

	fun run(previous: Map<String, Any>?, current: Map<String, Any>?): List<PipelineTask> {
		return CompiledPipelineRunnable(
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

/**
 * compiled pipeline is thread-safe and will be cached.
 */
class CompiledPipeline constructor(
	internal val pipeline: Pipeline,
	/** json string of prerequisite definition */
	internal val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	internal val prerequisiteTest: PrerequisiteTest,
	internal val stages: List<CompiledPipelineStage>
) {
	fun runnable(): CompiledPipelineRunnableBuilder {
		return CompiledPipelineRunnableBuilder(this)
	}
}

class PreparedPipelineCompiler(
	private val pipeline: Pipeline, private val principal: Principal
) : PreparedCompiler<CompiledPipeline> {
	override fun compile(): CompiledPipeline {
		return PipelineCompiler.of(this.pipeline).compileBy(this.principal)
	}
}

fun Pipeline.use(principal: Principal): PreparedPipelineCompiler {
	return PreparedPipelineCompiler(pipeline = this, principal = principal)
}
