package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineRunnableBuilder

/**
 * compiled pipeline is thread-safe and will be cached.
 */
class CompiledPipeline constructor(
	val pipeline: Pipeline,
	/** json string of prerequisite definition */
	val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	val prerequisiteTest: PrerequisiteTest,
	/** compiled stages */
	val stages: List<CompiledPipelineStage>
) {
	fun runnable(): PipelineRunnableBuilder {
		return PipelineRunnableBuilder(this)
	}
}
