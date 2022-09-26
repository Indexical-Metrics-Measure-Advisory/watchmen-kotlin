package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryConstantParameter
import com.matrdata.watchmen.model.admin.*

class CompiledAlarmAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: AlarmAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	val variables: CompiledVariables,
	/** json string of prerequisite definition */
	val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	val prerequisiteTest: PrerequisiteTest,
	val message: CompiledInMemoryConstantParameter
) : CompiledAction<SystemActionType, AlarmAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef
)