package com.matrdata.watchmen.pipeline.kernel.compile.condition

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.Joint
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables
import com.matrdata.watchmen.pipeline.kernel.compile.Compiler
import com.matrdata.watchmen.pipeline.kernel.compile.PreparedCompiler

sealed interface CompiledInMemoryCondition<C : Condition> {
	fun getCondition(): C
	fun test(variables: PipelineVariables, principal: Principal): Boolean
}

sealed interface InMemoryConditionCompiler<C : Condition, CC : CompiledInMemoryCondition<C>> : Compiler<CC> {
	companion object {
		fun of(condition: Condition): InMemoryConditionCompiler<out Condition, out CompiledInMemoryCondition<out Condition>> {
			return when (condition) {
				is Joint -> InMemoryJointCompiler.of(condition)
				is Expression<out ParameterExpressionOperator> -> InMemoryExpressionCompiler.of(condition)
			}
		}
	}
}

class PreparedInMemoryConditionCompiler(
	private val condition: Condition, private val principal: Principal
) : PreparedCompiler<CompiledInMemoryCondition<out Condition>> {
	override fun compile(): CompiledInMemoryCondition<out Condition> {
		return InMemoryConditionCompiler.of(this.condition).compileBy(this.principal)
	}
}