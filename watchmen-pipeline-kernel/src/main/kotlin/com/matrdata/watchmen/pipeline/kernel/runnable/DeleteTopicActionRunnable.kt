package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.model.admin.DeleteRowAction
import com.matrdata.watchmen.model.admin.DeleteRowsAction
import com.matrdata.watchmen.model.admin.DeleteTopicAction
import com.matrdata.watchmen.model.admin.DeleteTopicActionType
import com.matrdata.watchmen.model.runtime.monitor.DeleteActionMonitorLog
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledDeleteRowAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledDeleteRowsAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledDeleteTopicAction
import com.matrdata.watchmen.pipeline.kernel.utils.askNextIdAsStr
import java.time.LocalDateTime

abstract class DeleteTopicActionRunnable<A : DeleteTopicAction, CA : CompiledDeleteTopicAction<A>>(
	wrapped: PipelineActionRunnable<DeleteTopicActionType, A, CA>
) : TopicActionRunnable<DeleteTopicActionType, A, CA, DeleteActionMonitorLog>(wrapped) {
	override fun doCreateLog(): DeleteActionMonitorLog {
		return with(this.compiled) {
			DeleteActionMonitorLog(
				uid = askNextIdAsStr(),
				actionId = this.action.actionId, type = this.action.type,
				deleteCount = 0,
				status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null,
				definedAs = this.actionDef, touched = null
			)
		}
	}
}

class DeleteRowActionRunnable(
	wrapped: PipelineActionRunnable<DeleteTopicActionType, DeleteRowAction, CompiledDeleteRowAction>
) : DeleteTopicActionRunnable<DeleteRowAction, CompiledDeleteRowAction>(wrapped) {
	override fun doRun(log: DeleteActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}

class DeleteRowsActionRunnable(
	wrapped: PipelineActionRunnable<DeleteTopicActionType, DeleteRowsAction, CompiledDeleteRowsAction>
) : DeleteTopicActionRunnable<DeleteRowsAction, CompiledDeleteRowsAction>(wrapped) {
	override fun doRun(log: DeleteActionMonitorLog): Boolean {
		TODO("Not yet implemented")
	}
}
