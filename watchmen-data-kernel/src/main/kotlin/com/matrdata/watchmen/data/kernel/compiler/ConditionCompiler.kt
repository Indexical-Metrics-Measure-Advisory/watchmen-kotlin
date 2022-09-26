package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Condition

class ConditionCompilerSwitcher(private val condition: Condition, private val principal: Principal) {
	fun inMemory(): PreparedInMemoryConditionCompiler {
		return PreparedInMemoryConditionCompiler(condition = this.condition, principal = this.principal)
	}

	fun inStorage(): PreparedInStorageConditionCompiler {
		return PreparedInStorageConditionCompiler(condition = this.condition, principal = this.principal)
	}
}

fun Condition.use(principal: Principal): ConditionCompilerSwitcher {
	return ConditionCompilerSwitcher(condition = this, principal = principal)
}
