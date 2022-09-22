package com.matrdata.watchmen.pipeline.kernel.compile

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.admin.PipelineActionType
import com.matrdata.watchmen.model.admin.PipelineStage
import com.matrdata.watchmen.model.admin.PipelineUnit
import com.matrdata.watchmen.pipeline.kernel.compile.conditional.PrerequisiteTest
import com.matrdata.watchmen.pipeline.kernel.compile.conditional.use
import com.matrdata.watchmen.utils.ValueUtils

class PipelineUnitCompiler private constructor(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit
) : Compiler<CompiledPipelineUnit> {
	companion object {
		fun of(pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit): PipelineUnitCompiler {
			return PipelineUnitCompiler(pipeline, stage, unit)
		}
	}

	override fun compileBy(principal: Principal): CompiledPipelineUnit {
		return CompiledPipelineUnit(
			pipeline = this.pipeline,
			stage = this.stage,
			unit = this.unit,
			// generate definition json string
			prerequisiteDef = unit.toPrerequisiteDefJSON(),
			// compile prerequisite to test function
			prerequisiteTest = unit.use(principal).compile(),
			actions = with(unit.`do`) {
				require(!this.isNullOrEmpty()) { "Action not exists in pipeline unit[$unit]." }
				this.map { it.within(pipeline, stage, unit).use(principal).compile() }
			}
		)
	}
}

class CompiledPipelineUnit constructor(
	private val pipeline: Pipeline,
	private val stage: PipelineStage,
	private val unit: PipelineUnit,
	/** json string of prerequisite definition */
	private val prerequisiteDef: DefJSON,
	/** function to test prerequisite */
	private val prerequisiteTest: PrerequisiteTest,
	private val actions: List<CompiledPipelineAction<out PipelineActionType>>
) {
	private val loopVariableName: String?
		get() = this.unit.loopVariableName
	private val hasLoop: Boolean
		get() = ValueUtils.isNotBlank(this.loopVariableName)
	// TODO not yet implemented
}

class PreparedPipelineUnitCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit,
	private val principal: Principal
) : PreparedCompiler<CompiledPipelineUnit> {
	override fun compile(): CompiledPipelineUnit {
		return PipelineUnitCompiler.of(this.pipeline, this.stage, this.unit).compileBy(this.principal)
	}
}

class PrePreparedPipelineUnitCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage, private val unit: PipelineUnit
) {
	fun use(principal: Principal): PreparedPipelineUnitCompiler {
		return PreparedPipelineUnitCompiler(
			pipeline = this.pipeline, stage = this.stage, unit = this.unit, principal = principal
		)
	}
}

fun PipelineUnit.within(pipeline: Pipeline, stage: PipelineStage): PrePreparedPipelineUnitCompiler {
	return PrePreparedPipelineUnitCompiler(pipeline, stage, this)
}
