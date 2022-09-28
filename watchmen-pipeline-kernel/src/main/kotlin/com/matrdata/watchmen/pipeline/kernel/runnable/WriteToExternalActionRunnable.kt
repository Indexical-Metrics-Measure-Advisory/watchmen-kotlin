package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.data.kernel.utils.askNextIdAsStr
import com.matrdata.watchmen.model.admin.SystemActionType
import com.matrdata.watchmen.model.admin.WriteToExternalAction
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.model.runtime.monitor.WriteToExternalActionMonitorLog
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledWriteToExternalAction
import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import com.matrdata.watchmen.utils.spentInMs
import java.time.LocalDateTime

class WriteToExternalActionRunnable(wrapped: PipelineActionRunnable<SystemActionType, WriteToExternalAction, CompiledWriteToExternalAction>) :
	PipelineActionRunnableWrapper<SystemActionType, WriteToExternalAction, CompiledWriteToExternalAction>(wrapped) {
	fun run(): Boolean {
		val actionLog = this.createLog()

		try {
			this.compiled.write(this.variables, this.principal)
		} catch (t: Throwable) {
			// unexpected throwable, log it on stage runtime log
			logger.error("Unexpected exception raised.", t)
			actionLog.apply {
				status = MonitorLogStatus.ERROR
				error = t.stackTraceToString()
			}
		}

		actionLog.spentInMills = actionLog.startTime?.spentInMs()?.toInt() ?: 0
		return actionLog.status != MonitorLogStatus.ERROR
	}

	private fun createLog(): WriteToExternalActionMonitorLog {
		return with(this.compiled) {
			WriteToExternalActionMonitorLog(
				uid = askNextIdAsStr(),
				actionId = this.action.actionId,
				status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null,
				definedAs = this.actionDef, touched = null
			)
		}.also { this.log.actions!!.add(it) }
	}
}