package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryConstantParameter
import com.matrdata.watchmen.data.kernel.compiler.InMemoryConstantParameterCompiler
import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.ConstantParameter
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledAlarmAction
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledVariables
import com.matrdata.watchmen.pipeline.kernel.compiled.PrerequisiteTest
import com.matrdata.watchmen.utils.handTo

class AlarmActionCompiler(
	private val pipeline: Pipeline, private val stage: PipelineStage,
	private val unit: PipelineUnit, private val action: AlarmAction,
	private val variables: CompiledVariables
) : PipelineActionCompiler<SystemActionType, AlarmAction, CompiledAlarmAction> {
	companion object {
		fun of(
			pipeline: Pipeline, stage: PipelineStage, unit: PipelineUnit, action: AlarmAction,
			variables: CompiledVariables
		): AlarmActionCompiler {
			return AlarmActionCompiler(pipeline, stage, unit, action, variables)
		}
	}

	private fun compilePrerequisiteTest(variables: CompiledVariables, principal: Principal): PrerequisiteTest {
		// TODO use variables on prerequisite compiling
		return ConditionalCompiler.of(this.action).compileBy(principal)
	}

	private fun compileMessage(variables: CompiledVariables, principal: Principal): CompiledInMemoryConstantParameter {
		// TODO use variables on message compiling
		return InMemoryConstantParameterCompiler.of(ConstantParameter(value = this.action.message)).compileBy(principal)
	}

	override fun compileBy(principal: Principal): CompiledAlarmAction {
		return this.variables.copy().handTo { variablesOnAction ->
			CompiledAlarmAction(
				pipeline = this.pipeline,
				stage = this.stage,
				unit = this.unit,
				action = this.action,
				// generate definition json string of action itself
				actionDef = this.action.toDefJSON(),
				variables = variablesOnAction,
				// generate definition json string of prerequisite
				prerequisiteDef = this.action.toPrerequisiteDefJSON(),
				// compile prerequisite to test function
				prerequisiteTest = this.compilePrerequisiteTest(variablesOnAction, principal),
				message = this.compileMessage(variablesOnAction, principal)
			)
		}
	}
}