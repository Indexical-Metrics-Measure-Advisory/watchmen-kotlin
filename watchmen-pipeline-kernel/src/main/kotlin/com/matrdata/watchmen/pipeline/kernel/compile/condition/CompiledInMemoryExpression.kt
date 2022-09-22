package com.matrdata.watchmen.pipeline.kernel.compile.condition

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.*
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables
import com.matrdata.watchmen.pipeline.kernel.compile.Compiler
import com.matrdata.watchmen.pipeline.kernel.compile.parameter.CompiledInMemoryParameter
import com.matrdata.watchmen.pipeline.kernel.compile.parameter.use

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
				left = requireNotNull(this.expression.left) {
					"Left of expression[${this.expression}] cannot be null."
				}.use(principal).inMemory().compile()
			)

			is DualExpression -> CompiledInMemoryExpression(
				expression = this.expression,
				// compile left
				left = requireNotNull(this.expression.left) {
					"Left of expression[${this.expression}] cannot be null."
				}.use(principal).inMemory().compile(),
				// compile right
				right = requireNotNull(this.expression.right) {
					"Right of expression[${this.expression}] cannot be null."
				}.use(principal).inMemory().compile(),
			)
		}
	}
}

/**
 * compiled in-memory expression
 */
class CompiledInMemoryExpression constructor(
	private val expression: Expression<out ParameterExpressionOperator>,
	private val left: CompiledInMemoryParameter<out Parameter>,
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