package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageCondition
import com.matrdata.watchmen.data.kernel.compiler.use
import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.pipeline.kernel.PipelineKernelException
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledReadRowAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledReadTopicAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.utils.askTopicSchema
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfNull2

interface ReadTopicActionCompiler<A : ReadTopicAction, CA : CompiledReadTopicAction<A>> :
	PipelineActionCompiler<ReadTopicActionType, A, CA> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: ReadTopicAction,
			variables: CompiledVariables
		): ReadTopicActionCompiler<out ReadTopicAction, out CompiledReadTopicAction<out ReadTopicAction>> {
			return when (action) {
				is ReadRowAction -> ReadRowActionCompiler(pipeline, stage, unit, action, variables)
				is ReadRowsAction -> TODO("Not yet implemented")
				is ReadFactorAction -> TODO("Not yet implemented")
				is ReadFactorsAction -> TODO("Not yet implemented")
				is ExistsAction -> TODO("Not yet implemented")
			}
		}
	}
}

private fun ReadTopicAction.compileCondition(
	topicSchema: TopicSchema, variables: CompiledVariables, principal: Principal
): CompiledInStorageCondition<out Condition> {
	// TODO use variables on filter by compiling
	val actionId = this.actionId
	return this.by.throwIfNull2 {
		PipelineKernelException("By of action[id=$actionId] cannot be null.")
	}.use(principal).inStorage().compile()
}

class ReadRowActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: ReadRowAction,
	private val variables: CompiledVariables
) : ReadTopicActionCompiler<ReadRowAction, CompiledReadRowAction> {
	override fun compileBy(principal: Principal): CompiledReadRowAction {
		val topicSchema = this.action.askTopicSchema(principal)
		return this.variables.copy().handTo { variablesOnAction ->
			CompiledReadRowAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				topicSchema = topicSchema,
				by = this.action.compileCondition(topicSchema, this.variables, principal)
			)
		}
	}
}