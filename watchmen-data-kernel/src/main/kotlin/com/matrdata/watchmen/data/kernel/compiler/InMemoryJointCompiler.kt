package com.matrdata.watchmen.data.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.compiled.CompiledInMemoryJoint
import com.matrdata.watchmen.model.common.Joint

/**
 * in-memory joint compiler
 */
class InMemoryJointCompiler private constructor(private val joint: Joint) :
	InMemoryConditionCompiler<Joint, CompiledInMemoryJoint>, Compiler<CompiledInMemoryJoint> {
	companion object {
		fun of(joint: Joint): InMemoryJointCompiler {
			return InMemoryJointCompiler(joint)
		}
	}

	override fun compileBy(principal: Principal): CompiledInMemoryJoint {
		return CompiledInMemoryJoint(
			joint = this.joint,
			// compile sub filters, use empty list when it is null
			filters = this.joint.filters?.map { it.use(principal).inMemory().compile() } ?: listOf()
		)
	}
}
