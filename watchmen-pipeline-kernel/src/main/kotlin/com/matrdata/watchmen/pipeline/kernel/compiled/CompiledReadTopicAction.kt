package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageCondition
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.Condition

abstract class CompiledReadTopicAction<A : ReadTopicAction>(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: A,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where read from */
	topicSchema: TopicSchema,
	/** filter to read data */
	by: CompiledInStorageCondition<out Condition>?
) : CompiledFindByAction<ReadTopicActionType, A>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema, by = by
) {
	/**
	 * returns empty string when variable name is null
	 */
	val variableName: String get() = this.action.variableName ?: ""
}

class CompiledReadRowAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: ReadRowAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where read from */
	topicSchema: TopicSchema,
	/** filter to read data */
	by: CompiledInStorageCondition<out Condition>?
) : CompiledReadTopicAction<ReadRowAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema, by = by
)

class CompiledReadRowsAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: ReadRowsAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where read from */
	topicSchema: TopicSchema,
	/** filter to read data */
	by: CompiledInStorageCondition<out Condition>?
) : CompiledReadTopicAction<ReadRowsAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema, by = by
)

class CompiledExistsAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: ExistsAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where read from */
	topicSchema: TopicSchema,
	/** filter to read data */
	by: CompiledInStorageCondition<out Condition>?
) : CompiledReadTopicAction<ExistsAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema, by = by
)

abstract class CompiledReadTopicFactorAction<A : ReadTopicAction>(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: A,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where read from */
	topicSchema: TopicSchema,
	/** factor where read from */
	val factor: Factor,
	/** filter to read data */
	by: CompiledInStorageCondition<out Condition>?
) : CompiledReadTopicAction<A>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema, by = by
)

class CompiledReadFactorAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: ReadFactorAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where read from */
	topicSchema: TopicSchema,
	/** factor where read from */
	factor: Factor,
	/** filter to read data */
	by: CompiledInStorageCondition<out Condition>?
) : CompiledReadTopicFactorAction<ReadFactorAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema, factor = factor, by = by
)

class CompiledReadFactorsAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: ReadFactorAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where read from */
	topicSchema: TopicSchema,
	/** factor where read from */
	factor: Factor,
	/** filter to read data */
	by: CompiledInStorageCondition<out Condition>?
) : CompiledReadTopicFactorAction<ReadFactorAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema, factor = factor, by = by
)