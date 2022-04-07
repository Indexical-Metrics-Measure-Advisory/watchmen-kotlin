package com.matrdata.model.admin

import com.matrdata.model.base.*
import com.matrdata.model.common.Parameter
import com.matrdata.model.common.ParameterJoint
import java.util.*

interface Conditional {
	var conditional: Boolean?
	var on: ParameterJoint?
}

interface PipelineActionType {
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

interface PipelineAction<Type : PipelineActionType> {
	var actionId: PipelineActionId?
	var type: Type?
}

interface MemoryWriter<Type : PipelineActionType> : PipelineAction<Type> {
	var variableName: String?
}


interface FromTopic<Type : PipelineActionType> : PipelineAction<Type> {
	var topicId: TopicId?
}

interface FromFactor<Type : PipelineActionType> : FromTopic<Type> {
	var factorId: FactorId?
}

interface ToTopic<Type : PipelineActionType> : PipelineAction<Type> {
	var topicId: TopicId?
}

interface ToFactor<Type : PipelineActionType> : ToTopic<Type> {
	var factorId: FactorId?
}

interface FindBy<Type : PipelineActionType> : PipelineAction<Type> {
	var by: ParameterJoint?
}

enum class AggregateArithmetic(val code: String) {
	NONE("none"),
	COUNT("count"),
	SUM("sum"),
	AVG("avg")
}

interface AggregateArithmeticHolder {
	var arithmetic: AggregateArithmetic?
}

enum class AlarmActionSeverity(val code: String) {
	LOW("low"),
	MEDIUM("medium"),
	HIGH("high"),
	CRITICAL("critical")
}

class AlarmAction(
	override var actionId: PipelineActionId? = null,
	override var type: SystemActionType? = SystemActionType.ALARM,
	var severity: AlarmActionSeverity = AlarmActionSeverity.MEDIUM,
	var message: String? = null,
	override var conditional: Boolean? = null,
	override var on: ParameterJoint? = null
) : PipelineAction<SystemActionType>, Conditional

class CopyToMemoryAction(
	override var actionId: PipelineActionId? = null,
	override var type: SystemActionType? = SystemActionType.COPY_TO_MEMORY,
	var source: Parameter? = null,
	override var variableName: String? = null
) : MemoryWriter<SystemActionType>

class WriteToExternalAction(
	override var actionId: PipelineActionId? = null,
	override var type: SystemActionType? = SystemActionType.WRITE_TO_EXTERNAL,
	var externalWriterId: ExternalWriterId? = null,
	var eventCode: String? = null
) : PipelineAction<SystemActionType>

interface ReadTopicAction : FromTopic<ReadTopicActionType>, MemoryWriter<ReadTopicActionType>,
	FindBy<ReadTopicActionType>

class ReadRowAction(
	override var actionId: PipelineActionId? = null,
	override var type: ReadTopicActionType? = ReadTopicActionType.READ_ROW,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var by: ParameterJoint? = null
) : ReadTopicAction

class ReadRowActions(
	override var actionId: PipelineActionId? = null,
	override var type: ReadTopicActionType? = ReadTopicActionType.READ_ROWS,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var by: ParameterJoint? = null
) : ReadTopicAction

class ReadFactorAction(
	override var actionId: PipelineActionId? = null,
	override var type: ReadTopicActionType? = ReadTopicActionType.READ_FACTOR,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null,
	override var arithmetic: AggregateArithmetic? = AggregateArithmetic.NONE,
	override var by: ParameterJoint? = null
) : FromFactor<ReadTopicActionType>, ReadTopicAction, AggregateArithmeticHolder

class ReadFactorsAction(
	override var actionId: PipelineActionId? = null,
	override var type: ReadTopicActionType? = ReadTopicActionType.READ_FACTORS,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null,
	override var by: ParameterJoint? = null
) : FromFactor<ReadTopicActionType>, ReadTopicAction

class ExistsAction(
	override var actionId: PipelineActionId? = null,
	override var type: ReadTopicActionType? = ReadTopicActionType.EXISTS,
	override var variableName: String? = null,
	override var topicId: TopicId? = null,
	override var by: ParameterJoint? = null
) : ReadTopicAction

class MappingFactor(
	var source: Parameter? = null,
	var factorId: FactorId? = null,
	override var arithmetic: AggregateArithmetic? = AggregateArithmetic.NONE
) : AggregateArithmeticHolder

interface MappingRow : PipelineAction<WriteTopicActionType> {
	var mapping: List<MappingFactor>?
}

interface WriteTopicAction : ToTopic<WriteTopicActionType>

class InsertRowAction(
	override var actionId: PipelineActionId? = null,
	override var type: WriteTopicActionType? = WriteTopicActionType.INSERT_ROW,
	override var topicId: TopicId? = null,
	override var mapping: List<MappingFactor>? = mutableListOf()
) : WriteTopicAction, MappingRow

class InsertOrMergeRowAction(
	override var actionId: PipelineActionId? = null,
	override var type: WriteTopicActionType? = WriteTopicActionType.INSERT_OR_MERGE_ROW,
	override var topicId: TopicId? = null,
	override var mapping: List<MappingFactor>? = mutableListOf(),
	override var by: ParameterJoint? = null
) : WriteTopicAction, MappingRow, FindBy<WriteTopicActionType>

class MergeRowAction(
	override var actionId: PipelineActionId? = null,
	override var type: WriteTopicActionType? = WriteTopicActionType.MERGE_ROW,
	override var topicId: TopicId? = null,
	override var mapping: List<MappingFactor>? = mutableListOf(),
	override var by: ParameterJoint? = null
) : WriteTopicAction, MappingRow, FindBy<WriteTopicActionType>

class WriteFactorAction(
	override var actionId: PipelineActionId? = null,
	override var type: WriteTopicActionType? = WriteTopicActionType.WRITE_FACTOR,
	override var topicId: TopicId? = null,
	override var factorId: FactorId? = null,
	override var arithmetic: AggregateArithmetic? = AggregateArithmetic.NONE,
	var source: Parameter? = null,
	override var by: ParameterJoint? = null
) : WriteTopicAction, ToFactor<WriteTopicActionType>, FindBy<WriteTopicActionType>, AggregateArithmeticHolder

interface DeleteTopicAction : ToTopic<DeleteTopicActionType>, FindBy<DeleteTopicActionType>

class DeleteRowAction(
	override var actionId: PipelineActionId? = null,
	override var type: DeleteTopicActionType? = DeleteTopicActionType.DELETE_ROW,
	override var topicId: TopicId? = null,
	override var by: ParameterJoint? = null
) : DeleteTopicAction

class DeleteRowsAction(
	override var actionId: PipelineActionId? = null,
	override var type: DeleteTopicActionType? = DeleteTopicActionType.DELETE_ROWS,
	override var topicId: TopicId? = null,
	override var by: ParameterJoint? = null
) : DeleteTopicAction

class PipelineUnit(
	var unitId: PipelineUnitId? = null,
	var name: String? = null,
	var loopVariableName: String? = null,
	var `do`: List<PipelineAction<SystemActionType>>? = mutableListOf(),
	override var conditional: Boolean? = null,
	override var on: ParameterJoint? = null
) : Conditional

class PipelineStage(
	var stageId: PipelineStageId? = null,
	var name: String? = null,
	var units: List<PipelineUnit>? = mutableListOf(),
	override var conditional: Boolean? = null,
	override var on: ParameterJoint? = null
) : Conditional

enum class PipelineTriggerType(val code: String) {
	INSERT("insert"),
	MERGE("merge"),
	INSERT_OR_MERGE("insert-or-merge"),
	DELETE("delete")
}

class Pipeline(
	var pipelineId: PipelineId? = null,
	var topicId: TopicId? = null,
	var name: String? = null,
	var type: PipelineTriggerType? = null,
	var stages: List<PipelineStage>? = mutableListOf(),
	var enabled: Boolean? = null,
	var validated: Boolean? = null,
	override var conditional: Boolean? = null,
	override var on: ParameterJoint? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : Conditional, TenantBasedTuple, OptimisticLock