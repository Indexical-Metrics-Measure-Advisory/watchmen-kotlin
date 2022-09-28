package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.runnable.RunnableContext
import com.matrdata.watchmen.model.common.Condition
import com.matrdata.watchmen.model.common.Joint
import com.matrdata.watchmen.model.common.ParameterJointType

/**
 * compiled in-storage joint
 */
class CompiledInStorageJoint constructor(
	private val joint: Joint,
	private val filters: List<CompiledInStorageCondition<out Condition>>
) : CompiledInStorageCondition<Joint> {
	private fun hasFilter(): Boolean = this.filters.isNotEmpty()

	private fun isConjunctOnAnd(): Boolean {
		return when (this.joint.jointType) {
			ParameterJointType.OR -> false
			ParameterJointType.AND -> true
			null -> true
		}
	}

	override fun run(variables: RunnableContext, principal: Principal): Any? {
		TODO("Not yet implemented")
	}
}