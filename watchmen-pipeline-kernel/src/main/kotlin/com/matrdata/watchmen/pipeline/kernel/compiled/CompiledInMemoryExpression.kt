package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

/**
 * compiled in-memory expression
 */
class CompiledInMemoryExpression constructor(
	private val expression: Expression<out ParameterExpressionOperator>,
	private val left: CompiledInMemoryParameter<out Parameter>?,
	private val right: CompiledInMemoryParameter<out Parameter>? = null
) : CompiledInMemoryCondition<Expression<out ParameterExpressionOperator>> {
	override fun getCondition(): Expression<out ParameterExpressionOperator> {
		return this.expression
	}

	private fun getOperator(): ParameterExpressionOperator {
		return requireNotNull(this.expression.operator) { "Operator of expression[${this.expression}] cannot be null." }
	}

	override fun test(variables: PipelineVariables, principal: Principal): Boolean {
		TODO("Not yet implemented")
	}
}