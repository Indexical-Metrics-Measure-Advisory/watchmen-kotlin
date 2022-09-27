package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineTriggerType
import com.matrdata.watchmen.model.common.PipelineTriggerTraceId
import com.matrdata.watchmen.pipeline.kernel.PipelineKernelException
import com.matrdata.watchmen.pipeline.kernel.PipelineTask
import com.matrdata.watchmen.pipeline.kernel.PipelineTaskBuilder
import com.matrdata.watchmen.pipeline.kernel.compiled.PipelineTrigger
import com.matrdata.watchmen.pipeline.kernel.utils.askPipelinesByTopicId
import com.matrdata.watchmen.utils.Slf4j.Companion.logger

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
		val pipelines = askPipelinesByTopicId(topic.topicId!!).filter {
			this.shouldRun(it, trigger.triggerType)
		}

		if (pipelines.isEmpty()) {
			logger.warn("No pipeline needs to be triggered by topic[id=${topic.topicId}, name=${topic.name}].")
			return listOf()
		}

		return pipelines.map { pipeline ->
			// create task on given pipeline
			PipelineTaskBuilder.of(pipeline)
				.data(trigger.previous, trigger.current)
				.traceBy(traceId)
				.dataId(trigger.dataId)
				.by(principal)
				// push created task to list
				.also { task -> this.tasks.add(task) }
		}
	}

	fun getTasks(): List<PipelineTask> {
		return this.tasks
	}
}
