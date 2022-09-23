package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineStage

class CompiledPipelineStage constructor(
	val pipeline: Pipeline,
	val stage: PipelineStage,
	/** variables assertion */
	val variables: CompiledVariables,
	/** json string of prerequisite definition */
	val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	val prerequisiteTest: PrerequisiteTest,
	val units: List<CompiledPipelineUnit>
) {
	// TODO not yet implemented
}
