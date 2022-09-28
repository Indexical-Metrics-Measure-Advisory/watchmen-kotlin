package com.matrdata.watchmen.data.kernel.compiled

import com.matrdata.watchmen.model.admin.FactorType

interface CompiledObject {
	val dependedDefs: List<Any>
}

/**
 * compiled object which is typed, use factor type to identify possible types.
 */
interface TypedCompiledObject : CompiledObject {
	val possibleTypes: List<FactorType>

	fun isTypeSupported(type: FactorType): Boolean = this.possibleTypes.contains(type)
}