package com.matrdata.watchmen.pipeline.kernel.compile.condition

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Condition

class ConditionCompilerSwitcher(private val condition: Condition, private val principal: Principal) {
	fun inMemory(): PreparedInMemoryConditionCompiler {
		return PreparedInMemoryConditionCompiler(condition = this.condition, principal = this.principal)
	}

	// TODO not yet implemented, in-storage condition compiler
//	fun inStorage(): PrepareInStorageConditionCompiler {
//		return PrepareInStorageConditionCompiler(condition = this.condition, principal = this.principal)
//	}
}

fun Condition.use(principal: Principal): ConditionCompilerSwitcher {
	return ConditionCompilerSwitcher(condition = this, principal = principal)
}
