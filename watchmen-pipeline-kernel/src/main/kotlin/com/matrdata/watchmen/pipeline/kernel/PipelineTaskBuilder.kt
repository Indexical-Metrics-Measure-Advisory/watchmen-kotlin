package com.matrdata.watchmen.pipeline.kernel

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.common.PipelineTriggerTraceId
import com.matrdata.watchmen.model.common.TopicDataId
import com.matrdata.watchmen.utils.throwIfTrue

/**
 * build pipeline task, will do validation.
 */
class PipelineTaskBuilder {
	companion object {
		/**
		 * create a builder for building runtime context
		 */
		fun of(pipeline: Pipeline): PipelineTaskBuilder {
			return PipelineTaskBuilder().apply { this.pipeline = pipeline }
		}
	}

	private var pipeline: Pipeline? = null
	private var previousData: Map<String, Any>? = null
	private var currentData: Map<String, Any>? = null
	private var traceId: PipelineTriggerTraceId? = null
	private var dataId: TopicDataId? = null

	/**
	 * set data id of trigger data. it can be traced data in storage.
	 */
	fun dataId(dataId: TopicDataId) = apply { this.dataId = dataId }

	/**
	 * if trigger data is from synonym and not traceable, explicitly declare data id as {@see RuntimePipelineContext#UNKNOWN_SYNONYM_DATA_ID}.
	 */
	fun unknownDataId() = dataId(PipelineTask.UNKNOWN_SYNONYM_DATA_ID)

	/**
	 * set trigger data, previous and current version, both with complete structure.
	 */
	fun data(previous: Map<String, Any>?, current: Map<String, Any>?) = apply {
		this.previousData = previous
		this.currentData = current
	}

	/**
	 * set trace id for pipeline running
	 */
	fun traceBy(traceId: PipelineTriggerTraceId) = apply { this.traceId = traceId }

	/**
	 * set principal who invoke this pipeline, and returns runtime context
	 */
	fun by(principal: Principal): PipelineTask {
		// at least one of previous/current data is not null, otherwise raise exception
		listOf(this.previousData, this.currentData).all { it == null }.throwIfTrue {
			IllegalArgumentException("Previous and current data cannot be null at the same time.")
		}

		return PipelineTask(
			pipeline = requireNotNull(this.pipeline) { "Pipeline is required for runtime context." },
			previousData = this.previousData, currentData = this.currentData,
			principal = principal,
			traceId = requireNotNull(this.traceId) { "Trace id is required for runtime context." },
			dataId = requireNotNull(this.dataId) { "Data id is required for runtime context." }
		)
	}
}