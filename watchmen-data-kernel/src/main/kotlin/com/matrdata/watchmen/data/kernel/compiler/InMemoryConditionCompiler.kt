package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.Joint
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryCondition
import com.matrdata.watchmen.utils.throwIfNull

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
	private val condition: Condition
) : PreparedCompiler<CompiledInMemoryCondition<out Condition>> {
	private var principal: Principal? = null

	fun use(principal: Principal) = apply { this.principal = principal }

	override fun compile(): CompiledInMemoryCondition<out Condition> {
		this.principal.throwIfNull { "Principal cannot be null on in-memory parameter compiling." }

		return InMemoryConditionCompiler.of(this.condition).compileBy(this.principal!!)
	}
}

fun Condition.inMemory(): PreparedInMemoryConditionCompiler {
	return PreparedInMemoryConditionCompiler(condition = this)
}