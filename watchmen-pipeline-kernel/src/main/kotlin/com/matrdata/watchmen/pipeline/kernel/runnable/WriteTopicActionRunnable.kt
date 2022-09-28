package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.model.runtime.monitor.WriteActionMonitorLog
import com.matrdata.watchmen.pipeline.kernel.compiled.*
import com.matrdata.watchmen.pipeline.kernel.utils.askNextIdAsStr
import java.time.LocalDateTime

abstract class WriteTopicActionRunnable<A : WriteTopicAction, CA : CompiledWriteTopicAction<A>>(
	wrapped: PipelineActionRunnable<WriteTopicActionType, A, CA>
) : TopicActionRunnable<WriteTopicActionType, A, CA, WriteActionMonitorLog>(wrapped) {
	override fun doCreateLog(): WriteActionMonitorLog {
		return with(this.compiled) {
			WriteActionMonitorLog(
				uid = askNextIdAsStr(),
				actionId = this.action.actionId, type = this.action.type,
				insertCount = 0, updateCount = 0,
				status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null,
				definedAs = this.actionDef, touched = null
			)
		}
	}
}

class InsertRowActionRunnable(
	wrapped: PipelineActionRunnable<WriteTopicActionType, InsertRowAction, CompiledInsertRowAction>
) : WriteTopicActionRunnable<InsertRowAction, CompiledInsertRowAction>(wrapped) {
	override fun doRun(log: WriteActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}

class MergeRowActionRunnable(
	wrapped: PipelineActionRunnable<WriteTopicActionType, MergeRowAction, CompiledMergeRowAction>
) : WriteTopicActionRunnable<MergeRowAction, CompiledMergeRowAction>(wrapped) {
	override fun doRun(log: WriteActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}

class InsertOrMergeRowActionRunnable(
	wrapped: PipelineActionRunnable<WriteTopicActionType, InsertOrMergeRowAction, CompiledInsertOrMergeRowAction>
) : WriteTopicActionRunnable<InsertOrMergeRowAction, CompiledInsertOrMergeRowAction>(wrapped) {
	override fun doRun(log: WriteActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}

class WriteFactorActionRunnable(
	wrapped: PipelineActionRunnable<WriteTopicActionType, WriteFactorAction, CompiledWriteFactorAction>
) : WriteTopicActionRunnable<WriteFactorAction, CompiledWriteFactorAction>(wrapped) {
	override fun doRun(log: WriteActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}
