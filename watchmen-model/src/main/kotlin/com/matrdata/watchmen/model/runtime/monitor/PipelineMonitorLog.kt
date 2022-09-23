package com.matrdata.watchmen.model.runtime.monitor

import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class MonitorLogStatus(val code: String) {
	DONE("DONE"),  // even step is ignored by prerequisite is false, it is treated as DONE
	IGNORED("IGNORED"),  // step never be touched
	ERROR("ERROR")  // exception occurred
}

sealed interface StandardMonitorLog : DataModel {
	var status: MonitorLogStatus?
	var startTime: LocalDateTime?  // keep none when step is ignored
	var spentInMills: Int?  // keep 0 when step is ignored
	var error: String?  // if status is ERROR
}

sealed interface ConditionalMonitorLog : StandardMonitorLog {
	var prerequisite: Boolean?  // result of prerequisite, True when it is not defined
	var prerequisiteDefinedAs: Any?  // definition of prerequisite
}

sealed class MonitorLogAction<T : PipelineActionType>(
	open var uid: MonitorLogActionId? = null,
	open var actionId: PipelineActionId? = null,
	open var type: T? = null,
	open var insertCount: Int? = 0,
	open var updateCount: Int? = 0,
	open var deleteCount: Int? = 0,
	open var definedAs: Any? = null,  // definition of action
	/**
	 * 	touched value,
	 * 	for deletion, update and insert, always be list of dict
	 * 	for read-exists, bool,
	 * 	for read-factor, no arithmetic, Any, depends on factor type
	 * 	for read-factor, arithmetic, Decimal
	 * 	for read-row, dict
	 * 	for read-rows, list of dict
	 */
	open var touched: Any? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null
) : StandardMonitorLog

data class AlarmActionMonitorLog(
	override var uid: MonitorLogActionId? = null,
	override var actionId: PipelineActionId? = null,
	override var prerequisite: Boolean? = false,
	override var prerequisiteDefinedAs: Any? = null,
	override var definedAs: Any? = null,
	override var touched: Any? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null,
) : MonitorLogAction<SystemActionType>(
	uid = uid, actionId = actionId, type = SystemActionType.ALARM,
	insertCount = 0, updateCount = 0, deleteCount = 0,
	definedAs = definedAs, touched = touched,
	status = status, startTime = startTime, spentInMills = spentInMills, error = error
), ConditionalMonitorLog {
	@Suppress("UNUSED_PARAMETER")
	override var type: SystemActionType?
		get() = SystemActionType.ALARM
		set(value) {}
}

data class CopyToMemoryActionMonitorLog(
	override var uid: MonitorLogActionId? = null,
	override var actionId: PipelineActionId? = null,
	override var prerequisite: Boolean? = false,
	override var prerequisiteDefinedAs: Any? = null,
	override var definedAs: Any? = null,
	override var touched: Any? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null,
) : MonitorLogAction<SystemActionType>(
	uid = uid, actionId = actionId, type = SystemActionType.COPY_TO_MEMORY,
	insertCount = 0, updateCount = 0, deleteCount = 0,
	definedAs = definedAs, touched = touched,
	status = status, startTime = startTime, spentInMills = spentInMills, error = error
), ConditionalMonitorLog {
	@Suppress("UNUSED_PARAMETER")
	override var type: SystemActionType?
		get() = SystemActionType.COPY_TO_MEMORY
		set(value) {}
}

data class WriteToExternalActionMonitorLog(
	override var uid: MonitorLogActionId? = null,
	override var actionId: PipelineActionId? = null,
	override var prerequisite: Boolean? = false,
	override var prerequisiteDefinedAs: Any? = null,
	override var definedAs: Any? = null,
	override var touched: Any? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null,
) : MonitorLogAction<SystemActionType>(
	uid = uid, actionId = actionId, type = SystemActionType.WRITE_TO_EXTERNAL,
	insertCount = 0, updateCount = 0, deleteCount = 0,
	definedAs = definedAs, touched = touched,
	status = status, startTime = startTime, spentInMills = spentInMills, error = error
), ConditionalMonitorLog {
	@Suppress("UNUSED_PARAMETER")
	override var type: SystemActionType?
		get() = SystemActionType.WRITE_TO_EXTERNAL
		set(value) {}
}

sealed class MonitorLogFindByAction<T : PipelineActionType>(
	override var uid: MonitorLogActionId? = null,
	override var actionId: PipelineActionId? = null,
	override var type: T? = null,
	override var insertCount: Int? = 0,
	override var updateCount: Int? = 0,
	override var deleteCount: Int? = 0,
	open var findBy: Any? = null, // runtime describing of find by
	override var definedAs: Any? = null,
	override var touched: Any? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null
) : MonitorLogAction<T>(
	uid = uid, actionId = actionId, type = type,
	insertCount = insertCount, updateCount = updateCount, deleteCount = deleteCount,
	definedAs = definedAs, touched = touched,
	status = status, startTime = startTime, spentInMills = spentInMills, error = error
)

data class ReadActionMonitorLog(
	override var uid: MonitorLogActionId? = null,
	override var actionId: PipelineActionId? = null,
	override var type: ReadTopicActionType? = null,
	override var insertCount: Int? = 0,
	override var findBy: Any? = null, // runtime describing of find by
	override var definedAs: Any? = null,
	override var touched: Any? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null
) : MonitorLogFindByAction<ReadTopicActionType>(
	uid = uid, actionId = actionId, type = type,
	insertCount = insertCount, updateCount = 0, deleteCount = 0, findBy = findBy,
	definedAs = definedAs, touched = touched,
	status = status, startTime = startTime, spentInMills = spentInMills, error = error
)

data class WriteActionMonitorLog(
	override var uid: MonitorLogActionId? = null,
	override var actionId: PipelineActionId? = null,
	override var type: WriteTopicActionType? = null,
	override var insertCount: Int? = 0,
	override var updateCount: Int? = 0,
	override var findBy: Any? = null, // runtime describing of find by
	override var definedAs: Any? = null,
	override var touched: Any? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null
) : MonitorLogFindByAction<WriteTopicActionType>(
	uid = uid, actionId = actionId, type = type,
	insertCount = insertCount, updateCount = updateCount, deleteCount = 0, findBy = findBy,
	definedAs = definedAs, touched = touched,
	status = status, startTime = startTime, spentInMills = spentInMills, error = error
)

data class DeleteActionMonitorLog(
	override var uid: MonitorLogActionId? = null,
	override var actionId: PipelineActionId? = null,
	override var type: DeleteTopicActionType? = null,
	override var deleteCount: Int? = 0,
	override var findBy: Any? = null, // runtime describing of find by
	override var definedAs: Any? = null,
	override var touched: Any? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null
) : MonitorLogFindByAction<DeleteTopicActionType>(
	uid = uid, actionId = actionId, type = type,
	insertCount = 0, updateCount = 0, deleteCount = deleteCount, findBy = findBy,
	definedAs = definedAs, touched = touched,
	status = status, startTime = startTime, spentInMills = spentInMills, error = error
)

data class UnitMonitorLog(
	var unitId: PipelineUnitId? = null,
	var name: String? = null,
	var loopVariableName: String? = null,
	var loopVariableValue: Any? = null,
	var actions: MutableList<MonitorLogAction<out PipelineActionType>>? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null,
	override var prerequisite: Boolean? = false,
	override var prerequisiteDefinedAs: Any? = null
) : ConditionalMonitorLog

data class StageMonitorLog(
	var stageId: PipelineStageId?,
	var name: String? = null,
	var units: MutableList<UnitMonitorLog>? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null,
	override var prerequisite: Boolean? = false,
	override var prerequisiteDefinedAs: Any? = null
) : ConditionalMonitorLog

data class PipelineMonitorLog(
	var uid: PipelineMonitorLogId?,
	var traceId: PipelineTriggerTraceId?,
	var pipelineId: PipelineId?,
	var topicId: TopicId?,
	var dataId: Long?,
	var oldValue: Any? = null,
	var newValue: Any? = null,
	var stages: MutableList<StageMonitorLog>? = null,
	override var status: MonitorLogStatus? = null,
	override var startTime: LocalDateTime? = null,
	override var spentInMills: Int? = 0,
	override var error: String? = null,
	override var prerequisite: Boolean? = false,
	override var prerequisiteDefinedAs: Any? = null
) : ConditionalMonitorLog

data class PipelineMonitorLogCriteria(
	var topicId: TopicId? = null,
	var pipelineId: PipelineId? = null,
	var startDate: String? = null,
	var endDate: String? = null,
	var status: MonitorLogStatus? = null,
	var traceId: PipelineTriggerTraceId? = null,
	var tenantId: TenantId? = null,
	override var pageNumber: Int = 1,
	override var pageSize: Int = 100,
) : Pageable
