package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledDeleteRowAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledDeleteRowsAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledDeleteTopicAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.utils.askTopicSchema
import com.matrdata.watchmen.utils.handTo

sealed interface DeleteTopicActionCompiler<A : DeleteTopicAction, CA : CompiledDeleteTopicAction<A>> :
	PipelineActionCompiler<DeleteTopicActionType, A, CA> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: DeleteTopicAction,
			variables: CompiledVariables
		): DeleteTopicActionCompiler<out DeleteTopicAction, out CompiledDeleteTopicAction<out DeleteTopicAction>> {
			return when (action) {
				is DeleteRowAction -> DeleteRowActionCompiler(pipeline, stage, unit, action, variables)
				is DeleteRowsAction -> DeleteRowsActionCompiler(pipeline, stage, unit, action, variables)
			}
		}
	}
}

class DeleteRowActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: DeleteRowAction,
	private val variables: CompiledVariables
) : DeleteTopicActionCompiler<DeleteRowAction, CompiledDeleteRowAction> {
	override fun compileBy(principal: Principal): CompiledDeleteRowAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledDeleteRowAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				by = this.action.compileFindBy(topicSchema, this.variables, principal)
			)
		}
	}
}

class DeleteRowsActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: DeleteRowsAction,
	private val variables: CompiledVariables
) : DeleteTopicActionCompiler<DeleteRowsAction, CompiledDeleteRowsAction> {
	override fun compileBy(principal: Principal): CompiledDeleteRowsAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledDeleteRowsAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				by = this.action.compileFindBy(topicSchema, this.variables, principal)
			)
		}
	}
}
