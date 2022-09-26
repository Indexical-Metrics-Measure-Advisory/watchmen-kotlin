package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.model.runtime.monitor.PipelineMonitorLog
import com.matrdata.watchmen.model.runtime.monitor.StageMonitorLog
import com.matrdata.watchmen.pipeline.kernel.TopicStorages
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineStage
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineUnit
import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import com.matrdata.watchmen.utils.doIfFalse
import com.matrdata.watchmen.utils.doIfTrue
import com.matrdata.watchmen.utils.spentInMs
import com.matrdata.watchmen.utils.throwIfNull
import java.time.LocalDateTime

class PipelineStageRunnable(
	private val pipeline: CompiledPipeline,
	private val compiled: CompiledPipelineStage,
	private val principal: Principal,
	private val storages: TopicStorages,
	private val createPipelineTask: CreatePipelineTask,
	private val log: PipelineMonitorLog,
	private val variables: PipelineVariables
) {
	private fun runUnit(unit: CompiledPipelineUnit, variables: PipelineVariables, log: StageMonitorLog): Boolean {
		return unit.runnable(this.pipeline, this.compiled)
			.inherit(log)
			.use(this.principal)
			.use(this.storages)
			.use(this.createPipelineTask)
			.run(variables)
	}

	private fun runUnits(variables: PipelineVariables, log: StageMonitorLog): Boolean {
		return this.compiled.units.fold(initial = true) { should, unit: CompiledPipelineUnit ->
			if (!should) {
				// ignore unit run when previous said should not
				false
			} else {
				this.runUnit(unit, variables, log)
			}
		}
	}

	fun run(): Boolean {
		val stageLog = this.createLog()
		try {
			this.compiled.prerequisiteTest(this.variables, this.principal)
				.doIfFalse {
					// prerequisite check returns false
					stageLog.prerequisite = false
					stageLog.status = MonitorLogStatus.DONE
					// return true to continue next stage
					return true
				}.doIfTrue {
					stageLog.prerequisite = true
					// run stages
					runUnits(variables, stageLog)
						// units run successfully
						.doIfTrue { stageLog.status = MonitorLogStatus.DONE }
						// error occurred in units running, log status on stage runtime log
						.doIfFalse { stageLog.status = MonitorLogStatus.ERROR }
				}
		} catch (t: Throwable) {
			// unexpected throwable, log it on stage runtime log
			logger.error("Unexpected exception raised.", t)
			stageLog.apply {
				status = MonitorLogStatus.ERROR
				error = t.stackTraceToString()
			}
		}

		stageLog.spentInMills = stageLog.startTime?.spentInMs()?.toInt() ?: 0
		return stageLog.status != MonitorLogStatus.ERROR
	}

	private fun createLog(): StageMonitorLog {
		return StageMonitorLog(
			stageId = this.compiled.stage.stageId, name = this.compiled.stage.name,
			prerequisite = true,
			prerequisiteDefinedAs = this.compiled.prerequisiteDef,
			units = mutableListOf(),
			status = MonitorLogStatus.DONE, startTime = LocalDateTime.now(), spentInMills = 0, error = null
		).also { this.log.stages!!.add(it) }
	}
}

class PipelineStageRunnableCommand(
	private val pipeline: CompiledPipeline, private val stage: CompiledPipelineStage
) {
	private var log: PipelineMonitorLog? = null
	private var principal: Principal? = null
	private var storages: TopicStorages? = null
	private var createPipelineTask: CreatePipelineTask? = null

	fun inherit(log: PipelineMonitorLog) = apply { this.log = log }
	fun use(principal: Principal) = apply { this.principal = principal }
	fun use(storages: TopicStorages) = apply { this.storages = storages }
	fun use(createPipelineTask: CreatePipelineTask) = apply { this.createPipelineTask = createPipelineTask }

	fun run(variables: PipelineVariables): Boolean {
		return PipelineStageRunnable(
			pipeline = this.pipeline,
			compiled = this.stage,
			principal = this.principal.throwIfNull { "Principal cannot be null when run stage." },
			storages = this.storages.throwIfNull { "Topic storages cannot be null when run stage." },
			createPipelineTask = this.createPipelineTask.throwIfNull { "Pipeline task creator cannot be null when run stage." },
			log = this.log.throwIfNull { "Pipeline monitor log cannot be null when run stage." },
			variables = variables
		).run()
	}
}