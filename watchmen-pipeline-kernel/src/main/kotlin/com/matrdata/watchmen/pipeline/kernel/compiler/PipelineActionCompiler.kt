package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.utils.throwIfNull

interface PipelineActionCompiler<T : PipelineActionType, A : PipelineAction<T>, CA : CompiledAction<T, A>> :
	Compiler<CA> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit,
			action: PipelineAction<out PipelineActionType>,
			variables: CompiledVariables
		): PipelineActionCompiler<out PipelineActionType, out PipelineAction<out PipelineActionType>, out CompiledAction<out PipelineActionType, out PipelineAction<out PipelineActionType>>> {
			return when (action) {
				// system actions
				is AlarmAction -> AlarmActionCompiler.of(pipeline, stage, unit, action, variables)
				is CopyToMemoryAction -> CopyToMemoryActionCompiler.of(pipeline, stage, unit, action, variables)
				is WriteToExternalAction -> WriteToExternalActionCompiler.of(pipeline, stage, unit, action, variables)
				// read actions
				is ReadTopicAction -> ReadTopicActionCompiler.of(pipeline, stage, unit, action, variables)
				// write actions
				is InsertRowAction -> TODO("Not yet implemented")
				is MergeRowAction -> TODO("Not yet implemented")
				is InsertOrMergeRowAction -> TODO("Not yet implemented")
				is WriteFactorAction -> TODO("Not yet implemented")
				// delete actions
				is DeleteRowAction -> TODO("Not yet implemented")
				is DeleteRowsAction -> TODO("Not yet implemented")
			}
		}
	}
}

class PreparedPipelineActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit,
	private val action: PipelineAction<out PipelineActionType>,
	private val variables: CompiledVariables,
	private val principal: Principal
) : PreparedCompiler<CompiledAction<out PipelineActionType, out PipelineAction<out PipelineActionType>>> {
	override fun compile(): CompiledAction<out PipelineActionType, out PipelineAction<out PipelineActionType>> {
		return PipelineActionCompiler.of(
			this.pipeline, this.stage, this.unit, this.action, this.variables
		).compileBy(this.principal)
	}
}

class PrePreparedPipelineActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit,
	private val action: PipelineAction<out PipelineActionType>
) {
	private var variables: CompiledVariables? = null
	fun inherit(variables: CompiledVariables): PrePreparedPipelineActionCompiler {
		this.variables = variables
		return this
	}

	fun use(principal: Principal): PreparedPipelineActionCompiler {
		return PreparedPipelineActionCompiler(
			pipeline = this.pipeline, stage = this.stage, unit = this.unit, action = this.action,
			variables = this.variables.throwIfNull { "Compiled variables cannot be null." },
			principal = principal
		)
	}
}

fun PipelineAction<out PipelineActionType>.within(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit
): PrePreparedPipelineActionCompiler {
	return PrePreparedPipelineActionCompiler(pipeline, stage, unit, this)
}
