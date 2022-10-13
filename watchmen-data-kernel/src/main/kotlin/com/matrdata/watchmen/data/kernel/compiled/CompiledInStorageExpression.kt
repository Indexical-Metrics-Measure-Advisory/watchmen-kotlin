package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.DataKernelException
import com.matrdata.watchmen.data.kernel.runnable.RuntimeVariables
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.Parameter
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.utils.throwIfNull2

/**
 * compiled in-storage expression
 */
class CompiledInStorageExpression constructor(
	val expression: Expression<out ParameterExpressionOperator>,
	val left: CompiledInStorageParameter<out Parameter>?,
	val right: CompiledInStorageParameter<out Parameter>? = null
) : CompiledInStorageCondition<Expression<out ParameterExpressionOperator>> {
	val operator: ParameterExpressionOperator
		get() = this.expression.operator.throwIfNull2 {
			DataKernelException("Operator of expression[$expression] cannot be null.")
		}

	override fun run(variables: RuntimeVariables, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}