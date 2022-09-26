package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.PipelineAction
import com.matrdata.watchmen.model.admin.PipelineActionType
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.model.runtime.monitor.StageMonitorLog
import com.matrdata.watchmen.model.runtime.monitor.UnitMonitorLog
import com.matrdata.watchmen.pipeline.kernel.TopicStorages
import com.matrdata.watchmen.pipeline.kernel.askIsParallelActionsInLoopUnit
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineStage
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineUnit
import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import com.matrdata.watchmen.utils.doIfFalse
import com.matrdata.watchmen.utils.doIfTrue
import com.matrdata.watchmen.utils.spentInMs
import com.matrdata.watchmen.utils.throwIfNull
import java.time.LocalDateTime

class PipelineUnitRunnable(
	private val pipeline: CompiledPipeline,
	private val stage: CompiledPipelineStage,
	private val compiled: CompiledPipelineUnit,
	private val principal: Principal,
	private val storages: TopicStorages,
	private val createPipelineTask: CreatePipelineTask,
	private val log: StageMonitorLog,
	private val variables: PipelineVariables
) {
	/**
	 * treat null as empty list, no need to run unit.
	 * returns true to continue pipeline
	 */
	private fun ignoreOnLoopWithNull(): Boolean {
		this.createLog(status = MonitorLogStatus.DONE, loopVariableValue = null)
		return true
	}

	private fun runAction(
		action: CompiledPipelineAction<out PipelineActionType, out PipelineAction<out PipelineActionType>>,
		variables: PipelineVariables, log: UnitMonitorLog
	): Boolean {
		return action.runnable(this.pipeline, this.stage, this.compiled)
			.inherit(log)
			.use(this.principal)
			.use(this.storages)
			.use(this.createPipelineTask)
			.run(variables)
	}

	private fun runActions(variables: PipelineVariables, log: UnitMonitorLog): Boolean {
		return this.compiled.actions.fold(initial = true) { should, action: CompiledPipelineAction<out PipelineActionType, out PipelineAction<out PipelineActionType>> ->
			if (!should) {
				// ignore action run when previous said should not
				false
			} else {
				this.runAction(action, variables, log)
			}
		}
	}

	/**
	 * run unit once by given variables
	 */
	private fun once(variables: PipelineVariables): Boolean {
		val loopVariableValue = this.variables.find(this.compiled.loopVariableName!!)
		val unitLog = this.createLog(status = MonitorLogStatus.DONE, loopVariableValue = loopVariableValue)

		try {
			this.compiled.prerequisiteTest(variables, this.principal)
				.doIfFalse {
					// prerequisite check returns false
					unitLog.prerequisite = false
					unitLog.status = MonitorLogStatus.DONE
					// return true to continue next unit
					return true
				}.doIfTrue {
					unitLog.prerequisite = true
					// run units
					runActions(variables, unitLog)
						// actions run successfully
						.doIfTrue { unitLog.status = MonitorLogStatus.DONE }
						// error occurred in units running, log status on unit runtime log
						.doIfFalse { unitLog.status = MonitorLogStatus.ERROR }
				}
		} catch (t: Throwable) {
			// unexpected throwable, log it on stage runtime log
			logger.error("Unexpected exception raised.", t)
			unitLog.apply {
				status = MonitorLogStatus.ERROR
				error = t.stackTraceToString()
			}
		}

		unitLog.spentInMills = unitLog.startTime?.spentInMs()?.toInt() ?: 0
		return unitLog.status != MonitorLogStatus.ERROR
	}

	/**
	 * run loop when loop variable value is list
	 */
	private fun runLoopWithList(loopVariableValue: List<Any?>): Boolean {
		// copy each element to variables
		if (askIsParallelActionsInLoopUnit()) {
			TODO("Not yet implemented")
		} else {
			return loopVariableValue.all { elementValue ->
				this.once(this.variables.copy().also { vars ->
					vars.put(this.compiled.loopVariableName!!, elementValue)
				})
			}
		}
	}

	/**
	 * neither null nor list, cannot handle it in loop.
	 * returns false to break pipeline
	 */
	private fun errorOnLoopRatherThanListAndNull(loopVariableValue: Any): Boolean {
		this.createLog(
			status = MonitorLogStatus.ERROR, loopVariableValue = loopVariableValue,
			error = "Value of loop variable[${this.compiled.loopVariableName}] must be a list, current is [${loopVariableValue}]."
		)
		return false
	}

	fun run(): Boolean {
		return if (this.compiled.hasLoop) {
			when (val loopVariableValue = this.variables.find(this.compiled.loopVariableName!!)) {
				null -> this.ignoreOnLoopWithNull()
				is List<*> -> this.runLoopWithList(loopVariableValue)
				else -> this.errorOnLoopRatherThanListAndNull(loopVariableValue)
			}
		} else {
			// no loop declared, run once
			this.once(this.variables)
		}
	}

	private fun createLog(status: MonitorLogStatus, loopVariableValue: Any?, error: String? = null): UnitMonitorLog {
		return UnitMonitorLog(
			unitId = this.compiled.unit.unitId, name = this.compiled.unit.name,
			prerequisite = true,
			prerequisiteDefinedAs = this.compiled.prerequisiteDef,
			loopVariableName = this.compiled.loopVariableName, loopVariableValue = loopVariableValue,
			actions = mutableListOf(),
			status = status, startTime = LocalDateTime.now(), spentInMills = 0, error = error
		).also { this.log.units!!.add(it) }
	}
}

class PipelineUnitRunnableCommand(
	private val pipeline: CompiledPipeline, private val stage: CompiledPipelineStage,
	private val unit: CompiledPipelineUnit
) {
	private var log: StageMonitorLog? = null
	private var principal: Principal? = null
	private var storages: TopicStorages? = null
	private var createPipelineTask: CreatePipelineTask? = null

	fun inherit(log: StageMonitorLog) = apply { this.log = log }
	fun use(principal: Principal) = apply { this.principal = principal }
	fun use(storages: TopicStorages) = apply { this.storages = storages }
	fun use(createPipelineTask: CreatePipelineTask) = apply { this.createPipelineTask = createPipelineTask }

	fun run(variables: PipelineVariables): Boolean {
		return PipelineUnitRunnable(
			pipeline = this.pipeline,
			stage = this.stage,
			compiled = this.unit,
			principal = this.principal.throwIfNull { "Principal cannot be null when run unit." },
			storages = this.storages.throwIfNull { "Topic storages cannot be null when run unit." },
			createPipelineTask = this.createPipelineTask.throwIfNull { "Pipeline task creator cannot be null when run unit." },
			log = this.log.throwIfNull { "Stage monitor log cannot be null when run unit." },
			variables = variables
		).run()
	}
}