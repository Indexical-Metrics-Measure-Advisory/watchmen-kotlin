package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageCondition
import com.matrdata.watchmen.data.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.Condition

abstract class CompiledDeleteTopicAction<A : DeleteTopicAction>(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: A,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	val variables: CompiledVariables,
	/** topic where data find by */
	val topicSchema: TopicSchema,
) : CompiledAction<DeleteTopicActionType, A>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef
)

class CompiledDeleteRowAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: DeleteRowAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where write to */
	topicSchema: TopicSchema,
	/** filter to write data */
	val by: CompiledInStorageCondition<out Condition>?
) : CompiledDeleteTopicAction<DeleteRowAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema
)

class CompiledDeleteRowsAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: DeleteRowsAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where write to */
	topicSchema: TopicSchema,
	/** filter to write data */
	val by: CompiledInStorageCondition<out Condition>?
) : CompiledDeleteTopicAction<DeleteRowsAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema
)
