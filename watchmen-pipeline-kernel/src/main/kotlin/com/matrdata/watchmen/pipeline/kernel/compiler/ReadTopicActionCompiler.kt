package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.pipeline.kernel.compiled.*
import com.matrdata.watchmen.pipeline.kernel.utils.askTopicSchema
import com.matrdata.watchmen.pipeline.kernel.utils.findFactorWithin
import com.matrdata.watchmen.utils.handTo

sealed interface ReadTopicActionCompiler<A : ReadTopicAction, CA : CompiledReadTopicAction<A>> :
	PipelineActionCompiler<ReadTopicActionType, A, CA> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: ReadTopicAction,
			variables: CompiledVariables
		): ReadTopicActionCompiler<out ReadTopicAction, out CompiledReadTopicAction<out ReadTopicAction>> {
			return when (action) {
				is ReadRowAction -> ReadRowActionCompiler(pipeline, stage, unit, action, variables)
				is ReadRowsAction -> ReadRowsActionCompiler(pipeline, stage, unit, action, variables)
				is ExistsAction -> ExistsActionCompiler(pipeline, stage, unit, action, variables)
				is ReadFactorAction -> ReadFactorActionCompiler(pipeline, stage, unit, action, variables)
				is ReadFactorsAction -> ReadFactorsActionCompiler(pipeline, stage, unit, action, variables)
			}
		}
	}
}

class ReadRowActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: ReadRowAction,
	private val variables: CompiledVariables
) : ReadTopicActionCompiler<ReadRowAction, CompiledReadRowAction> {
	override fun compileBy(principal: Principal): CompiledReadRowAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledReadRowAction(
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

class ReadRowsActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: ReadRowsAction,
	private val variables: CompiledVariables
) : ReadTopicActionCompiler<ReadRowsAction, CompiledReadRowsAction> {
	override fun compileBy(principal: Principal): CompiledReadRowsAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledReadRowsAction(
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

class ExistsActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: ExistsAction,
	private val variables: CompiledVariables
) : ReadTopicActionCompiler<ExistsAction, CompiledExistsAction> {
	override fun compileBy(principal: Principal): CompiledExistsAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledExistsAction(
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

class ReadFactorActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: ReadFactorAction,
	private val variables: CompiledVariables
) : ReadTopicActionCompiler<ReadFactorAction, CompiledReadFactorAction> {
	override fun compileBy(principal: Principal): CompiledReadFactorAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledReadFactorAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				factor = this.action.findFactorWithin(topicSchema),
				by = this.action.compileFindBy(topicSchema, this.variables, principal)
			)
		}
	}
}

class ReadFactorsActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: ReadFactorsAction,
	private val variables: CompiledVariables
) : ReadTopicActionCompiler<ReadFactorsAction, CompiledReadFactorsAction> {
	override fun compileBy(principal: Principal): CompiledReadFactorsAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledReadFactorsAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				factor = this.action.findFactorWithin(topicSchema),
				by = this.action.compileFindBy(topicSchema, this.variables, principal)
			)
		}
	}
}