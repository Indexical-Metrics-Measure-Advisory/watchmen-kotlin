package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledCopyToMemoryAction
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryParameter
import com.matrdata.watchmen.data.kernel.compiler.inMemory
import com.matrdata.watchmen.utils.handTo

class CopyToMemoryActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: CopyToMemoryAction,
	private val variables: CompiledVariables
) : PipelineActionCompiler<SystemActionType, CopyToMemoryAction, CompiledCopyToMemoryAction> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: CopyToMemoryAction,
			variables: CompiledVariables
		): CopyToMemoryActionCompiler {
			return CopyToMemoryActionCompiler(pipeline, stage, unit, action, variables)
		}
	}

	private fun compileSource(
		variables: CompiledVariables, principal: Principal
	): CompiledInMemoryParameter<out Parameter>? {
		// TODO use variables on source compiling
		return this.action.source?.inMemory()?.use(principal)?.compile()
	}

	override fun compileBy(principal: Principal): CompiledCopyToMemoryAction {
		return this.variables.copy().handTo { variablesOnAction ->
			CompiledCopyToMemoryAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				source = this.compileSource(variablesOnAction, principal)
			)
		}
	}
}