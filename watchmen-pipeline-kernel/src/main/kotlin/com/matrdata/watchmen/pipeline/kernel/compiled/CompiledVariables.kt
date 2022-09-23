package com.matrdata.watchmen.pipeline.kernel.compiled

class CompiledVariables constructor() {
	private val variables: MutableMap<String, Any> = mutableMapOf()

	private constructor(variables: CompiledVariables) : this() {
		this.variables.putAll(variables.variables)
	}

	/**
	 * inherit from this and create new one
	 */
	fun copy(): CompiledVariables {
		return CompiledVariables(this)
	}
}