package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.pipeline.kernel.compiled.*
import com.matrdata.watchmen.pipeline.kernel.utils.askTopicSchema
import com.matrdata.watchmen.utils.handTo

sealed interface WriteTopicActionCompiler<A : WriteTopicAction, CA : CompiledWriteTopicAction<A>> :
	PipelineActionCompiler<WriteTopicActionType, A, CA> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: WriteTopicAction,
			variables: CompiledVariables
		): WriteTopicActionCompiler<out WriteTopicAction, out CompiledWriteTopicAction<out WriteTopicAction>> {
			return when (action) {
				is InsertRowAction -> InsertRowActionCompiler(pipeline, stage, unit, action, variables)
				is MergeRowAction -> MergeRowActionCompiler(pipeline, stage, unit, action, variables)
				is InsertOrMergeRowAction -> InsertOrMergeRowActionCompiler(pipeline, stage, unit, action, variables)
				is WriteFactorAction -> WriteFactorActionCompiler(pipeline, stage, unit, action, variables)
			}
		}
	}
}

class InsertRowActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: InsertRowAction,
	private val variables: CompiledVariables
) : WriteTopicActionCompiler<InsertRowAction, CompiledInsertRowAction> {
	override fun compileBy(principal: Principal): CompiledInsertRowAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledInsertRowAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				mapping = this.action.compileMappingFactors(topicSchema, variables, principal)
			)
		}
	}
}

class MergeRowActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: MergeRowAction,
	private val variables: CompiledVariables
) : WriteTopicActionCompiler<MergeRowAction, CompiledMergeRowAction> {
	override fun compileBy(principal: Principal): CompiledMergeRowAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledMergeRowAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				mapping = this.action.compileMappingFactors(topicSchema, variables, principal),
				by = this.action.compileFindBy(topicSchema, this.variables, principal)
			)
		}
	}
}

class InsertOrMergeRowActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: InsertOrMergeRowAction,
	private val variables: CompiledVariables
) : WriteTopicActionCompiler<InsertOrMergeRowAction, CompiledInsertOrMergeRowAction> {
	override fun compileBy(principal: Principal): CompiledInsertOrMergeRowAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledInsertOrMergeRowAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				mapping = this.action.compileMappingFactors(topicSchema, variables, principal),
				by = this.action.compileFindBy(topicSchema, this.variables, principal)
			)
		}
	}
}

class WriteFactorActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: WriteFactorAction,
	private val variables: CompiledVariables
) : WriteTopicActionCompiler<WriteFactorAction, CompiledWriteFactorAction> {
	override fun compileBy(principal: Principal): CompiledWriteFactorAction {
		return Pair(
			this.action.askTopicSchema(principal),
			this.variables.copy()
		).handTo { topicSchema, variablesOnAction ->
			CompiledWriteFactorAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				// construct as a mapping factor
				mapping = MappingFactor(
					source = this.action.source,
					factorId = this.action.factorId,
					arithmetic = this.action.arithmetic
				).compile(topicSchema, variables, principal),
				by = this.action.compileFindBy(topicSchema, this.variables, principal)
			)
		}
	}
}