package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.model.runtime.monitor.ReadActionMonitorLog
import com.matrdata.watchmen.pipeline.kernel.compiled.*
import com.matrdata.watchmen.pipeline.kernel.utils.askNextIdAsStr
import java.time.LocalDateTime

abstract class ReadTopicActionRunnable<A : ReadTopicAction, CA : CompiledReadTopicAction<A>>(
	wrapped: PipelineActionRunnable<ReadTopicActionType, A, CA>
) : TopicActionRunnable<ReadTopicActionType, A, CA, ReadActionMonitorLog>(wrapped) {
	override fun doCreateLog(): ReadActionMonitorLog {
		return with(this.compiled) {
			ReadActionMonitorLog(
				uid = askNextIdAsStr(),
				actionId = this.action.actionId, type = this.action.type,
				status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null,
				definedAs = this.actionDef, touched = null
			)
		}
	}
}

class ReadRowActionRunnable(
	wrapped: PipelineActionRunnable<ReadTopicActionType, ReadRowAction, CompiledReadRowAction>
) : ReadTopicActionRunnable<ReadRowAction, CompiledReadRowAction>(wrapped) {
	override fun doRun(log: ReadActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}

class ReadRowsActionRunnable(
	wrapped: PipelineActionRunnable<ReadTopicActionType, ReadRowsAction, CompiledReadRowsAction>
) : ReadTopicActionRunnable<ReadRowsAction, CompiledReadRowsAction>(wrapped) {
	override fun doRun(log: ReadActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}

class ExistsActionRunnable(
	wrapped: PipelineActionRunnable<ReadTopicActionType, ExistsAction, CompiledExistsAction>
) : ReadTopicActionRunnable<ExistsAction, CompiledExistsAction>(wrapped) {
	override fun doRun(log: ReadActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}

class ReadFactorActionRunnable(
	wrapped: PipelineActionRunnable<ReadTopicActionType, ReadFactorAction, CompiledReadFactorAction>
) : ReadTopicActionRunnable<ReadFactorAction, CompiledReadFactorAction>(wrapped) {
	override fun doRun(log: ReadActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}

class ReadFactorsActionRunnable(
	wrapped: PipelineActionRunnable<ReadTopicActionType, ReadFactorsAction, CompiledReadFactorsAction>
) : ReadTopicActionRunnable<ReadFactorsAction, CompiledReadFactorsAction>(wrapped) {
	override fun doRun(log: ReadActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}
