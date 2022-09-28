package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageExpression
import com.matrdata.watchmen.model.common.DualExpression
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.model.common.SingleExpression

/**
 * in-storage expression compiler
 */
class InStorageExpressionCompiler private constructor(private val expression: Expression<out ParameterExpressionOperator>) :
	InStorageConditionCompiler<Expression<out ParameterExpressionOperator>, CompiledInStorageExpression>,
	Compiler<CompiledInStorageExpression> {
	companion object {
		fun of(expression: Expression<out ParameterExpressionOperator>): InStorageExpressionCompiler {
			return InStorageExpressionCompiler(expression)
		}
	}

	override fun compileBy(principal: Principal): CompiledInStorageExpression {
		return when (this.expression) {
			is SingleExpression -> CompiledInStorageExpression(
				expression = this.expression,
				// compile left
				left = this.expression.left?.inStorage()?.use(principal)?.compile()
			)

			is DualExpression -> CompiledInStorageExpression(
				expression = this.expression,
				// compile left
				left = this.expression.left?.inStorage()?.use(principal)?.compile(),
				// compile right
				right = this.expression.right?.inStorage()?.use(principal)?.compile(),
			)
		}
	}
}
