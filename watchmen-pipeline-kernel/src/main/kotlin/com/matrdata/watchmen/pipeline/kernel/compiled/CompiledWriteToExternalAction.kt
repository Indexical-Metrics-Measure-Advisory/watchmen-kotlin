package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.*

class CompiledWriteToExternalAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: WriteToExternalAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	val variables: CompiledVariables,
	val write: WriteToExternal
) : CompiledAction<SystemActionType, WriteToExternalAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef
)