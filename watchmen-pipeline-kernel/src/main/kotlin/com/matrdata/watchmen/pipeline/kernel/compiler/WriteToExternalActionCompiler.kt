package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.pnp.ExternalWriterParams
import com.matrdata.watchmen.data.kernel.pnp.ExternalWriterRegistry
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.pipeline.kernel.askExternalWriterService
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledWriteToExternalAction
import com.matrdata.watchmen.pipeline.kernel.compiled.WriteToExternal
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfBlank
import com.matrdata.watchmen.utils.throwIfNull

class WriteToExternalActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: WriteToExternalAction,
	private val variables: CompiledVariables
) : PipelineActionCompiler<SystemActionType, WriteToExternalAction, CompiledWriteToExternalAction> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: WriteToExternalAction,
			variables: CompiledVariables
		): WriteToExternalActionCompiler {
			return WriteToExternalActionCompiler(pipeline, stage, unit, action, variables)
		}
	}


	private fun compileWrite(
		@Suppress("UNUSED_PARAMETER") variables: CompiledVariables,
		@Suppress("UNUSED_PARAMETER") principal: Principal
	): WriteToExternal {
		return { v: PipelineVariables, p: Principal ->
			val def = action.externalWriterId.throwIfBlank {
				"External writer not declared in action[pipelineId=${pipeline.pipelineId}, stageId=${stage.stageId}, unitId=${unit.unitId}, actionId=${action.actionId}]."
			}.handTo { writerId: String ->
				askExternalWriterService(p).findById(writerId).throwIfNull {
					"External writer[id=$writerId] not found."
				}.also {
					it.writerCode.throwIfBlank { "Code of external writer cannot[id=$writerId] be blank." }
					it.url.throwIfBlank { "Url of external writer cannot[id=$writerId] be blank." }
				}
			}

			ExternalWriterRegistry.INSTANCE.askWriter(def.writerCode!!).run(
				ExternalWriterParams(
					pat = def.pat,
					url = def.url,
					eventCode = this.action.eventCode,
					currentData = v.getCurrentData(),
					previousData = v.getPreviousData(),
					variables = v.getVariableData()
				)
			)
		}
	}

	override fun compileBy(principal: Principal): CompiledWriteToExternalAction {
		return this.variables.copy().handTo { variablesOnAction ->
			CompiledWriteToExternalAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				write = this.compileWrite(variablesOnAction, principal)
			)
		}
	}
}