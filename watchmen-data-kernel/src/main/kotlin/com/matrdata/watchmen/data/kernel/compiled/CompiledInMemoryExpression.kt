package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.DataKernelException
import com.matrdata.watchmen.data.kernel.runnable.RunnableContext
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.utils.throwIfNull2

/**
 * compiled in-memory expression
 */
class CompiledInMemoryExpression constructor(
	val expression: Expression<out ParameterExpressionOperator>,
	val left: CompiledInMemoryParameter<out Parameter>?,
	val right: CompiledInMemoryParameter<out Parameter>? = null
) : CompiledInMemoryCondition<Expression<out ParameterExpressionOperator>> {
	val operator: ParameterExpressionOperator
		get() = this.expression.operator.throwIfNull2 {
			DataKernelException("Operator of expression[$expression] cannot be null.")
		}

	override fun test(variables: RunnableContext, principal: Principal): Boolean {
		TODO("Not yet implemented")
	}
}