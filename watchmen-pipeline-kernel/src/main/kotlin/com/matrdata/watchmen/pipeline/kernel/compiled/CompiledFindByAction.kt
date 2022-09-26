package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageCondition
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.Condition

abstract class CompiledFindByAction<T : PipelineActionType, A : FindBy<T>>(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: A,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	val variables: CompiledVariables,
	/** topic where data find by */
	val topicSchema: TopicSchema,
	val by: CompiledInStorageCondition<out Condition>?
) : CompiledAction<T, A>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef
)