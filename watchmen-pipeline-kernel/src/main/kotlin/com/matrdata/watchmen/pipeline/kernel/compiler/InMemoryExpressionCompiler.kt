package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.DualExpression
import com.matrdata.watchmen.model.common.Expression
import com.matrdata.watchmen.model.common.ParameterExpressionOperator
import com.matrdata.watchmen.model.common.SingleExpression
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInMemoryExpression

/**
 * in-memory expression compiler
 */
class InMemoryExpressionCompiler private constructor(private val expression: Expression<out ParameterExpressionOperator>) :
	InMemoryConditionCompiler<Expression<out ParameterExpressionOperator>, CompiledInMemoryExpression>,
	Compiler<CompiledInMemoryExpression> {
	companion object {
		fun of(expression: Expression<out ParameterExpressionOperator>): InMemoryExpressionCompiler {
			return InMemoryExpressionCompiler(expression)
		}
	}

	override fun compileBy(principal: Principal): CompiledInMemoryExpression {
		return when (this.expression) {
			is SingleExpression -> CompiledInMemoryExpression(
				expression = this.expression,
				// compile left
				left = this.expression.left?.inMemory()?.use(principal)?.compile()
			)

			is DualExpression -> CompiledInMemoryExpression(
				expression = this.expression,
				// compile left
				left = this.expression.left?.inMemory()?.use(principal)?.compile(),
				// compile right
				right = this.expression.right?.inMemory()?.use(principal)?.compile(),
			)
		}
	}
}