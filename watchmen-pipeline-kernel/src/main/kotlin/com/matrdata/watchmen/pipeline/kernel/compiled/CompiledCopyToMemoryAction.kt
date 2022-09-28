package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.data.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.Parameter

class CompiledCopyToMemoryAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: CopyToMemoryAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	val variables: CompiledVariables,
	val source: CompiledInMemoryParameter<out Parameter>?
) : CompiledAction<SystemActionType, CopyToMemoryAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef
) {
	/**
	 * returns empty string when variable name is null
	 */
	val variableName: String get() = this.action.variableName ?: ""
}