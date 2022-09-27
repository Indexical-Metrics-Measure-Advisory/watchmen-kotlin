package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInStorageJoint
import com.matrdata.watchmen.model.common.Joint

/**
 * in-storage joint compiler
 */
class InStorageJointCompiler private constructor(private val joint: Joint) :
	InStorageConditionCompiler<Joint, CompiledInStorageJoint>, Compiler<CompiledInStorageJoint> {
	companion object {
		fun of(joint: Joint): InStorageJointCompiler {
			return InStorageJointCompiler(joint)
		}
	}

	override fun compileBy(principal: Principal): CompiledInStorageJoint {
		return CompiledInStorageJoint(
			joint = this.joint,
			// compile sub filters, use empty list when it is null
			filters = this.joint.filters?.map { it.use(principal).inStorage().compile() } ?: listOf()
		)
	}
}
