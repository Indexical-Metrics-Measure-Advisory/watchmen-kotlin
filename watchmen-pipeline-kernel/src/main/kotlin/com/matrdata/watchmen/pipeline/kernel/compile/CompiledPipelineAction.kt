package com.matrdata.watchmen.pipeline.kernel.compile

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.*

sealed class CompiledPipelineAction<T : PipelineActionType>(
	private val pipeline: Pipeline,
	private val stage: PipelineStage,
	private val unit: PipelineUnit,
	private val action: PipelineAction<T>,
	/** json string of action definition */
	private val actionDef: DefJSON
)

sealed interface PipelineActionCompiler<T : PipelineActionType, A : CompiledPipelineAction<T>> : Compiler<A> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit,
			action: PipelineAction<out PipelineActionType>
		): PipelineActionCompiler<out PipelineActionType, out CompiledPipelineAction<out PipelineActionType>> {
			return when (action) {
				is AlarmAction -> TODO("Not yet implemented") // AlarmActionCompiler.of(pipeline, stage, unit, action)
				is CopyToMemoryAction -> TODO("Not yet implemented")
				is WriteToExternalAction -> TODO("Not yet implemented")
				is InsertOrMergeRowAction -> TODO("Not yet implemented")
				is MergeRowAction -> TODO("Not yet implemented")
				is ExistsAction -> TODO("Not yet implemented")
				is ReadFactorAction -> TODO("Not yet implemented")
				is ReadFactorsAction -> TODO("Not yet implemented")
				is ReadRowAction -> TODO("Not yet implemented")
				is ReadRowsAction -> TODO("Not yet implemented")
				is WriteFactorAction -> TODO("Not yet implemented")
				is InsertRowAction -> TODO("Not yet implemented")
			}
		}
	}
}

class PreparedPipelineActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit,
	private val action: PipelineAction<out PipelineActionType>,
	private val principal: Principal
) : PreparedCompiler<CompiledPipelineAction<out PipelineActionType>> {
	override fun compile(): CompiledPipelineAction<out PipelineActionType> {
		return PipelineActionCompiler.of(this.pipeline, this.stage, this.unit, this.action).compileBy(this.principal)
	}
}

class PrePreparedPipelineActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit,
	private val action: PipelineAction<out PipelineActionType>
) {
	fun use(principal: Principal): PreparedPipelineActionCompiler {
		return PreparedPipelineActionCompiler(
			pipeline = this.pipeline, stage = this.stage, unit = this.unit, action = this.action,
			principal = principal
		)
	}
}

fun PipelineAction<out PipelineActionType>.within(
	pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit
): PrePreparedPipelineActionCompiler {
	return PrePreparedPipelineActionCompiler(pipeline, stage, unit, this)
}
