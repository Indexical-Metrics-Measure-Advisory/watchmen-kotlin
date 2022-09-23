package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.Joint
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInMemoryCondition

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