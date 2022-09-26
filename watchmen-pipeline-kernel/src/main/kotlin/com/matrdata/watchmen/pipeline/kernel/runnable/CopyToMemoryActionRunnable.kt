package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.model.admin.CopyToMemoryAction
import com.matrdata.watchmen.model.admin.SystemActionType
import com.matrdata.watchmen.model.runtime.monitor.CopyToMemoryActionMonitorLog
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.pipeline.kernel.askSnowflakeGenerator
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledCopyToMemoryAction
import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import com.matrdata.watchmen.utils.spentInMs
import java.time.LocalDateTime

class CopyToMemoryActionRunnable(wrapped: PipelineActionRunnable<SystemActionType, CopyToMemoryAction, CompiledCopyToMemoryAction>) :
	PipelineActionRunnableWrapper<SystemActionType, CopyToMemoryAction, CompiledCopyToMemoryAction>(wrapped) {
	fun run(): Boolean {
		val actionLog = this.createLog()

		try {
			val value = this.compiled.source?.value(this.variables, this.principal)
			actionLog.touched = mapOf("data" to value)
			// put into variables
			this.variables.put(this.compiled.variableName, value)
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

	private fun createLog(): CopyToMemoryActionMonitorLog {
		return CopyToMemoryActionMonitorLog(
			uid = askSnowflakeGenerator().nextIdAsStr(),
			actionId = this.compiled.action.actionId,
			status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null,
			definedAs = this.compiled.actionDef, touched = null
		).also { this.log.actions!!.add(it) }
	}
}