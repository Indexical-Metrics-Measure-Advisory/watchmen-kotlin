package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageCondition
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.Joint
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.utils.throwIfNull

sealed interface InStorageConditionCompiler<C : Condition, CC : CompiledInStorageCondition<C>> : Compiler<CC> {
	companion object {
		fun of(condition: Condition): InStorageConditionCompiler<out Condition, out CompiledInStorageCondition<out Condition>> {
			return when (condition) {
				is Joint -> InStorageJointCompiler.of(condition)
				is Expression<out ParameterExpressionOperator> -> InStorageExpressionCompiler.of(condition)
			}
		}
	}
}

class PreparedInStorageConditionCompiler(
	private val condition: Condition
) : PreparedCompiler<CompiledInStorageCondition<out Condition>> {
	private var principal: Principal? = null

	fun use(principal: Principal) = apply { this.principal = principal }

	override fun compile(): CompiledInStorageCondition<out Condition> {
		this.principal.throwIfNull { "Principal cannot be null on in-storage condition compiling." }

		return InStorageConditionCompiler.of(this.condition).compileBy(this.principal!!)
	}
}

fun Condition.inStorage(): PreparedInStorageConditionCompiler {
	return PreparedInStorageConditionCompiler(condition = this)
}

