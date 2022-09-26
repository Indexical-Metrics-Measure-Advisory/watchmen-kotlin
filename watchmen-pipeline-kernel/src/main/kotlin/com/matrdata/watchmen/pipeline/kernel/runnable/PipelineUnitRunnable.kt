package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.runtime.monitor.MonitorLogStatus
import com.matrdata.watchmen.model.runtime.monitor.StageMonitorLog
import com.matrdata.watchmen.model.runtime.monitor.UnitMonitorLog
import com.matrdata.watchmen.pipeline.kernel.TopicStorages
import com.matrdata.watchmen.pipeline.kernel.askIsParallelActionsInLoopUnit
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipeline
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineStage
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledPipelineUnit
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
	private fun once(variables: PipelineVariables): Boolean {
		TODO("Not yet implemented")
	}

	fun run(): Boolean {
		if (this.compiled.hasLoop) {
			when (val loopVariableValue = this.variables.find(this.compiled.loopVariableName!!)) {
				null -> {
					// treat null as empty list, no need to run unit
					this.createLog(MonitorLogStatus.DONE, null)
					// continue pipeline
					return true
				}

				is List<*> -> {
					// copy each element to variables
					if (askIsParallelActionsInLoopUnit()) {
						TODO("Not yet implemented")
					} else {
						return loopVariableValue.all { elementValue ->
							this.once(this.variables.copy().also { vars ->
								vars.put(compiled.loopVariableName!!, elementValue)
							})
						}
					}
				}

				else -> {
					// neither null nor list, cannot handle it in loop
					this.createLog(
						MonitorLogStatus.ERROR, loopVariableValue,
						"Value of loop variable[${this.compiled.loopVariableName}] must be a list, current is [${loopVariableValue}]."
					)
					// break pipeline
					return false
				}
			}
		} else {
			// no loop declared, run once
			return this.once(this.variables)
		}
	}

	private fun createLog(status: MonitorLogStatus, loopVariableValue: Any?, error: String? = null): UnitMonitorLog {
		return UnitMonitorLog(
			unitId = this.compiled.unit.unitId, name = this.compiled.unit.name,
			status = status, startTime = LocalDateTime.now(), spentInMills = 0, error = error,
			loopVariableName = this.compiled.loopVariableName, loopVariableValue = loopVariableValue,
			actions = mutableListOf()
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