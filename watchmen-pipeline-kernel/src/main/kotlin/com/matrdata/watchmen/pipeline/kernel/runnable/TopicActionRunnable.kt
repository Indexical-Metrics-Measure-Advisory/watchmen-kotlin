package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.model.admin.PipelineAction
import com.matrdata.watchmen.model.admin.PipelineActionType
import com.matrdata.watchmen.model.runtime.monitor.ActionMonitorLog
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledAction
import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import com.matrdata.watchmen.utils.spentInMs

abstract class TopicActionRunnable<T : PipelineActionType, A : PipelineAction<T>, CA : CompiledAction<T, A>, L : ActionMonitorLog<T>>(
	wrapped: PipelineActionRunnable<T, A, CA>
) : PipelineActionRunnableWrapper<T, A, CA>(wrapped) {
	internal abstract fun doRun(log: L): Boolean

	fun run(): Boolean {
		val actionLog = this.createLog()

		try {
			this.doRun(actionLog)
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

	internal abstract fun doCreateLog(): L

	private fun createLog(): L {
		return this.doCreateLog().also { this.log.actions!!.add(it) }
	}
}
