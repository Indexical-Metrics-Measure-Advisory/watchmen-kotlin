package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryCondition
import com.matrdata.watchmen.data.kernel.compiler.use
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.model.common.Conditional
import com.matrdata.watchmen.pipeline.kernel.compiled.PrerequisiteTest

val TEST_PREREQUISITE_ALWAYS_TRUE: PrerequisiteTest = { _, _ -> true }

/**
 * create a function to test prerequisite at runtime
 */
private fun CompiledInMemoryCondition<out Condition>.createTest(): PrerequisiteTest {
	return { variables, principal -> this.test(variables, principal) }
}

class ConditionalCompiler private constructor(private val conditional: Conditional) : Compiler<PrerequisiteTest> {
	companion object {
		fun of(conditional: Conditional): ConditionalCompiler {
			return ConditionalCompiler(conditional)
		}
	}

	override fun compileBy(principal: Principal): PrerequisiteTest {
		return when {
			// no conditional, always return true
			this.conditional.conditional != true -> TEST_PREREQUISITE_ALWAYS_TRUE
			// no condition declared, always return true
			this.conditional.on == null -> TEST_PREREQUISITE_ALWAYS_TRUE
			// no condition declared, always return true
			this.conditional.on?.filters == null -> TEST_PREREQUISITE_ALWAYS_TRUE
			// no condition declared, always return true
			this.conditional.on?.filters?.size == 0 -> TEST_PREREQUISITE_ALWAYS_TRUE
			// compile condition, prerequisite is always in memory, create test function
			else -> this.conditional.on!!.use(principal).inMemory().compile().createTest()
		}
	}
}
