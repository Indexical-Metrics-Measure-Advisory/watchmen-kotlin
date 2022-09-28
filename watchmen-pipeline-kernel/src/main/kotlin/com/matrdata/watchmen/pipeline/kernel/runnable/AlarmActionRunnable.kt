package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.model.admin.AlarmAction
import com.matrdata.watchmen.model.admin.AlarmActionSeverity
import com.matrdata.watchmen.model.admin.SystemActionType
import com.matrdata.watchmen.model.runtime.monitor.AlarmActionMonitorLog
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledAlarmAction
import com.matrdata.watchmen.pipeline.kernel.utils.askNextIdAsStr
import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import com.matrdata.watchmen.utils.doIfFalse
import com.matrdata.watchmen.utils.doIfTrue
import com.matrdata.watchmen.utils.spentInMs
import java.time.LocalDateTime

class AlarmActionRunnable(wrapped: PipelineActionRunnable<SystemActionType, AlarmAction, CompiledAlarmAction>) :
	PipelineActionRunnableWrapper<SystemActionType, AlarmAction, CompiledAlarmAction>(wrapped) {
	fun run(): Boolean {
		val actionLog = this.createLog()

		try {
			this.compiled.prerequisiteTest(this.variables, this.principal)
				.doIfFalse {
					// prerequisite check returns false
					actionLog.prerequisite = false
				}.doIfTrue {
					actionLog.prerequisite = true
					val value = compiled.message.value(variables, principal)
					actionLog.touched = mapOf("data" to value)
					val severity = (compiled.action.severity ?: AlarmActionSeverity.MEDIUM).code.uppercase()
					// TODO alarm message is logged on error level default
					logger.error("[PIPELINE] [ALARM] [$severity] $value")
				}
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

	private fun createLog(): AlarmActionMonitorLog {
		return with(this.compiled) {
			AlarmActionMonitorLog(
				uid = askNextIdAsStr(),
				actionId = this.action.actionId,
				status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null,
				prerequisite = true, prerequisiteDefinedAs = this.prerequisiteDef,
				definedAs = this.actionDef, touched = null
			)
		}.also { this.log.actions!!.add(it) }
	}
}