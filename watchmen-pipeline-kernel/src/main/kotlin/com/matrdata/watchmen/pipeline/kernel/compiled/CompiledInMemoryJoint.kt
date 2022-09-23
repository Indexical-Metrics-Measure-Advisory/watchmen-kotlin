package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.model.common.Joint
import com.matrdata.watchmen.model.common.ParameterJointType
import com.matrdata.watchmen.pipeline.kernel.runnable.PipelineVariables

/**
 * compiled in-memory joint
 */
class CompiledInMemoryJoint constructor(
	private val joint: Joint,
	private val filters: List<CompiledInMemoryCondition<out Condition>>
) : CompiledInMemoryCondition<Joint> {
	override fun getCondition(): Joint {
		return this.joint
	}

	private fun hasFilter(): Boolean {
		return this.filters.isNotEmpty()
	}

	private fun isConjunctOnAnd(): Boolean {
		return when (this.joint.jointType) {
			ParameterJointType.OR -> false
			ParameterJointType.AND -> true
			null -> true
		}
	}

	override fun test(variables: PipelineVariables, principal: Principal): Boolean {
		return if (this.hasFilter()) {
			when (this.isConjunctOnAnd()) {
				// a or b [ or c [ or d ...]]
				false -> this.filters.any { it.test(variables, principal) }
				// a and b [ and c [ and d ...]]
				true -> this.filters.all { it.test(variables, principal) }
			}
		} else {
			// no filter declared, always returns true no matter what the conjunction is
			true
		}
	}
}