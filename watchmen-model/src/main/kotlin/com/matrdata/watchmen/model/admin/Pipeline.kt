package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

sealed interface Conditional {
	var conditional: Boolean?
	var on: ParameterJoint?
}

sealed interface PipelineActionType {
	val code: String
}

enum class SystemActionType(override val code: String) : PipelineActionType {
	ALARM("alarm"),
	COPY_TO_MEMORY("copy-to-memory"),
	WRITE_TO_EXTERNAL("write-to-external")
}

enum class ReadTopicActionType(override val code: String) : PipelineActionType {
	READ_ROW("read-row"),
	READ_FACTOR("read-factor"),
	EXISTS("exists"),
	READ_ROWS("read-rows"),
	READ_FACTORS("read-factors")
}

enum class WriteTopicActionType(override val code: String) : PipelineActionType {
	MERGE_ROW("merge-row"),
	INSERT_ROW("insert-row"),
	INSERT_OR_MERGE_ROW("insert-or-merge-row"),
	WRITE_FACTOR("write-factor")
}

enum class DeleteTopicActionType(override val code: String) : PipelineActionType {
	DELETE_ROW("delete-row"),
	DELETE_ROWS("delete-rows")
}

sealed interface PipelineAction<T : PipelineActionType> : DataModel {
	var actionId: PipelineActionId?
	var type: T
}

sealed interface MemoryWriter<T : PipelineActionType> : PipelineAction<T> {
	var variableName: String?
}

sealed interface FromTopic<T : PipelineActionType> : PipelineAction<T> {
	var topicId: TopicId?
}

sealed interface FromFactor<T : PipelineActionType> : FromTopic<T> {
	var factorId: FactorId?
}

sealed interface ToTopic<T : PipelineActionType> : PipelineAction<T> {
	var topicId: TopicId?
}

sealed interface ToFactor<T : PipelineActionType> : ToTopic<T> {
	var factorId: FactorId?
}

sealed interface FindBy<T : PipelineActionType> : PipelineAction<T> {
	var by: ParameterJoint?
}

enum class AggregateArithmetic(val code: String) {
	NONE("none"),
	COUNT("count"),
	SUM("sum"),
	AVG("avg")
}

sealed interface AggregateArithmeticHolder : DataModel {
	var arithmetic: AggregateArithmetic?
}

sealed interface PipelineSystemAction : PipelineAction<SystemActionType>

enum class AlarmActionSeverity(val code: String) {
	LOW("low"),
	MEDIUM("medium"),
	HIGH("high"),
	CRITICAL("critical")
}

data class AlarmAction(
	override var actionId: PipelineActionId? = null,
	var severity: AlarmActionSeverity = AlarmActionSeverity.MEDIUM,
	var message: String? = null,
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null
) : PipelineSystemAction, Conditional {
	@Suppress("UNUSED_PARAMETER")
	override var type: SystemActionType
		get() = SystemActionType.ALARM
		set(value) {}
}

/**
 * copy something to memory variable
 */
data class CopyToMemoryAction(
	override var actionId: PipelineActionId?,
	override var variableName: String?,
	var source: Parameter? = null
) : PipelineSystemAction, MemoryWriter<SystemActionType> {
	@Suppress("UNUSED_PARAMETER")
	override var type: SystemActionType
		get() = SystemActionType.COPY_TO_MEMORY
		set(value) {}
}

data class WriteToExternalAction(
	override var actionId: PipelineActionId?,
	var externalWriterId: ExternalWriterId? = null,
	var eventCode: String? = null
) : PipelineSystemAction {
	@Suppress("UNUSED_PARAMETER")
	override var type: SystemActionType
		get() = SystemActionType.WRITE_TO_EXTERNAL
		set(value) {}
}

sealed interface ReadTopicAction : FromTopic<ReadTopicActionType>, MemoryWriter<ReadTopicActionType>,
	FindBy<ReadTopicActionType>

data class ReadRowAction(
	override var actionId: PipelineActionId? = null,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var by: ParameterJoint? = null
) : ReadTopicAction {
	@Suppress("UNUSED_PARAMETER")
	override var type: ReadTopicActionType
		get() = ReadTopicActionType.READ_ROW
		set(value) {}
}

data class ReadRowsAction(
	override var actionId: PipelineActionId? = null,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var by: ParameterJoint? = null
) : ReadTopicAction {
	@Suppress("UNUSED_PARAMETER")
	override var type: ReadTopicActionType
		get() = ReadTopicActionType.READ_ROWS
		set(value) {}
}

data class ReadFactorAction(
	override var actionId: PipelineActionId? = null,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null,
	override var arithmetic: AggregateArithmetic? = null,
	override var by: ParameterJoint? = null
) : ReadTopicAction, FromFactor<ReadTopicActionType>, AggregateArithmeticHolder {
	@Suppress("UNUSED_PARAMETER")
	override var type: ReadTopicActionType
		get() = ReadTopicActionType.READ_FACTOR
		set(value) {}
}


data class ReadFactorsAction(
	override var actionId: PipelineActionId? = null,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null,
	override var by: ParameterJoint? = null
) : ReadTopicAction, FromFactor<ReadTopicActionType> {
	@Suppress("UNUSED_PARAMETER")
	override var type: ReadTopicActionType
		get() = ReadTopicActionType.READ_FACTORS
		set(value) {}
}

data class ExistsAction(
	override var actionId: PipelineActionId? = null,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var by: ParameterJoint? = null
) : ReadTopicAction {
	@Suppress("UNUSED_PARAMETER")
	override var type: ReadTopicActionType
		get() = ReadTopicActionType.EXISTS
		set(value) {}
}

data class MappingFactor(
	var source: Parameter? = null,
	var factorId: FactorId? = null,
	override var arithmetic: AggregateArithmetic? = null
) : AggregateArithmeticHolder

sealed interface MappingRow : PipelineAction<WriteTopicActionType> {
	var mapping: List<MappingFactor>?
}

enum class AccumulateMode(val code: String) {
	// add value in current data for insert
	// subtract value in previous data , add value in current data for merge
	STANDARD("standard"),

	// allowed only on explicit merge action (merge row/write factor)
	// subtract value in previous data
	REVERSE("reverse"),

	// force cumulate, not matter there is previous or not. always accumulate to existing value
	// not allowed on insert action. actually for explicit insert action, behaviour is same as standard mode
	// ignore previous data even existing, add value in current data only
	CUMULATE("cumulate")
}

sealed interface WriteTopicAction : ToTopic<WriteTopicActionType> {
	var accumulateMode: AccumulateMode?
}

data class InsertRowAction(
	override var actionId: PipelineActionId? = null,
	override var topicId: TopicId? = null,
	override var mapping: List<MappingFactor>? = null,
	override var accumulateMode: AccumulateMode? = AccumulateMode.STANDARD
) : WriteTopicAction, MappingRow {
	@Suppress("UNUSED_PARAMETER")
	override var type: WriteTopicActionType
		get() = WriteTopicActionType.INSERT_ROW
		set(value) {}
}

data class InsertOrMergeRowAction(
	override var actionId: PipelineActionId? = null,
	override var topicId: TopicId? = null,
	override var mapping: List<MappingFactor>? = null,
	override var accumulateMode: AccumulateMode? = AccumulateMode.STANDARD,
	override var by: ParameterJoint? = null
) : WriteTopicAction, MappingRow, FindBy<WriteTopicActionType> {
	@Suppress("UNUSED_PARAMETER")
	override var type: WriteTopicActionType
		get() = WriteTopicActionType.INSERT_OR_MERGE_ROW
		set(value) {}
}

data class MergeRowAction(
	override var actionId: PipelineActionId? = null,
	override var topicId: TopicId? = null,
	override var mapping: List<MappingFactor>? = null,
	override var accumulateMode: AccumulateMode? = AccumulateMode.STANDARD,
	override var by: ParameterJoint? = null
) : WriteTopicAction, MappingRow, FindBy<WriteTopicActionType> {
	@Suppress("UNUSED_PARAMETER")
	override var type: WriteTopicActionType
		get() = WriteTopicActionType.MERGE_ROW
		set(value) {}
}

data class WriteFactorAction(
	override var actionId: PipelineActionId? = null,
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null,
	override var arithmetic: AggregateArithmetic? = null,
	override var accumulateMode: AccumulateMode? = null,
	override var by: ParameterJoint? = null
) : WriteTopicAction, ToFactor<WriteTopicActionType>, FindBy<WriteTopicActionType>, AggregateArithmeticHolder {
	@Suppress("UNUSED_PARAMETER")
	override var type: WriteTopicActionType
		get() = WriteTopicActionType.WRITE_FACTOR
		set(value) {}
}

data class PipelineUnit(
	var unitId: PipelineUnitId? = null,
	var name: String? = null,
	var loopVariableName: String? = null,
	var `do`: List<PipelineAction<in PipelineActionType>>? = null,
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null
) : Conditional, DataModel

data class PipelineStage(
	var stageId: PipelineStageId? = null,
	var name: String? = null,
	var units: List<PipelineUnit>? = null,
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null
) : Conditional, DataModel

enum class PipelineTriggerType(val code: String) {
	INSERT("insert"),
	MERGE("merge"),
	INSERT_OR_MERGE("insert-or-merge"),
	DELETE("delete")
}

data class Pipeline(
	var pipelineId: PipelineId? = null,
	var topicId: TopicId? = null,
	var name: String? = null,
	var type: PipelineTriggerType? = null,
	var stages: List<PipelineStage>? = null,
	var enabled: Boolean? = null,
	var validated: Boolean? = null,
	override var conditional: Boolean? = false,
	override var on: ParameterJoint? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : Conditional, TenantBasedTuple, OptimisticLock
	