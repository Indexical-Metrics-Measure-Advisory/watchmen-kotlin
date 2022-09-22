package com.matrdata.watchmen.pipeline.kernel.compile.conditional

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.model.common.Conditional
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables
import com.matrdata.watchmen.pipeline.kernel.compile.Compiler
import com.matrdata.watchmen.pipeline.kernel.compile.PreparedCompiler
import com.matrdata.watchmen.pipeline.kernel.compile.condition.CompiledInMemoryCondition
import com.matrdata.watchmen.pipeline.kernel.compile.condition.use

typealias PrerequisiteTest = (PipelineVariables, Principal) -> Boolean

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


class PreparedConditionalCompiler(
	private val conditional: Conditional, private val principal: Principal
) : PreparedCompiler<PrerequisiteTest> {
	override fun compile(): PrerequisiteTest {
		return ConditionalCompiler.of(this.conditional).compileBy(this.principal)
	}
}

fun Conditional.use(principal: Principal): PreparedConditionalCompiler {
	return PreparedConditionalCompiler(conditional = this, principal = principal)
}
