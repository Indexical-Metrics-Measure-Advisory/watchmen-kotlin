package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.PipelineVariables
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.model.common.ParameterExpressionOperator

/**
 * compiled in-storage expression
 */
class CompiledInStorageExpression constructor(
	private val expression: Expression<out ParameterExpressionOperator>,
	private val left: CompiledInStorageParameter<out Parameter>?,
	private val right: CompiledInStorageParameter<out Parameter>? = null
) : CompiledInStorageCondition<Expression<out ParameterExpressionOperator>> {
	override fun getCondition(): Expression<out ParameterExpressionOperator> {
		return this.expression
	}

	private fun getOperator(): ParameterExpressionOperator {
		return requireNotNull(this.expression.operator) { "Operator of expression[${this.expression}] cannot be null." }
	}

	override fun run(variables: PipelineVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}