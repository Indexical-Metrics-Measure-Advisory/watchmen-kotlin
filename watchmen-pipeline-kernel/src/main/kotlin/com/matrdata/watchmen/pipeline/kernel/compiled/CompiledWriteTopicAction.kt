package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageCondition
import com.matrdata.watchmen.data.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.Condition

abstract class CompiledWriteTopicAction<A : WriteTopicAction>(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: A,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	val variables: CompiledVariables,
	/** topic where data find by */
	val topicSchema: TopicSchema,
) : CompiledAction<WriteTopicActionType, A>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef
)

class CompiledInsertRowAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: InsertRowAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where write to */
	topicSchema: TopicSchema,
	val mapping: List<CompiledInStorageMappingFactor>,
) : CompiledWriteTopicAction<InsertRowAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema
) {
	/**
	 * return standard mode when mode is not declared
	 */
	val accumulateMode: AccumulateMode get() = this.action.accumulateMode ?: AccumulateMode.STANDARD
}

class CompiledMergeRowAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: MergeRowAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where write to */
	topicSchema: TopicSchema,
	val mapping: List<CompiledInStorageMappingFactor>,
	/** filter to write data */
	val by: CompiledInStorageCondition<out Condition>?
) : CompiledWriteTopicAction<MergeRowAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema
) {
	/**
	 * return standard mode when mode is not declared
	 */
	val accumulateMode: AccumulateMode get() = this.action.accumulateMode ?: AccumulateMode.STANDARD
}

class CompiledInsertOrMergeRowAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: InsertOrMergeRowAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where write to */
	topicSchema: TopicSchema,
	val mapping: List<CompiledInStorageMappingFactor>,
	/** filter to write data */
	val by: CompiledInStorageCondition<out Condition>?
) : CompiledWriteTopicAction<InsertOrMergeRowAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema
) {
	/**
	 * return standard mode when mode is not declared
	 */
	val accumulateMode: AccumulateMode get() = this.action.accumulateMode ?: AccumulateMode.STANDARD
}

class CompiledWriteFactorAction(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: WriteFactorAction,
	/** json string of action definition */
	actionDef: DefJSON,
	/** compiled variables */
	variables: CompiledVariables,
	/** topic where write to */
	topicSchema: TopicSchema,
	val mapping: CompiledInStorageMappingFactor,
	/** filter to write data */
	val by: CompiledInStorageCondition<out Condition>?
) : CompiledWriteTopicAction<WriteFactorAction>(
	pipeline = pipeline, stage = stage, unit = unit, action = action, actionDef = actionDef,
	variables = variables,
	topicSchema = topicSchema
) {
	/**
	 * return standard mode when mode is not declared
	 */
	val accumulateMode: AccumulateMode get() = this.action.accumulateMode ?: AccumulateMode.STANDARD
}
