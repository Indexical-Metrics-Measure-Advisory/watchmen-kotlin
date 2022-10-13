package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.data.kernel.DataKernelException
import com.matrdata.watchmen.data.kernel.runnable.RuntimeVariables
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfNullOrEmpty2

class PipelineVariables(
	private val previousData: Map<String, Any?>?, private val currentData: Map<String, Any?>?
) : RuntimeVariables() {
	companion object {
		const val FROM_PREVIOUS: String = "&old"
		const val FROM_PREVIOUS_PREFIX: String = "${FROM_PREVIOUS}."
	}

	fun getPreviousData(): Map<String, Any?>? = this.previousData
	fun getCurrentData(): Map<String, Any?>? = this.currentData

	fun getVariableData(): Map<String, Any?> = this.map

	/**
	 * find value by given names
	 */
	private fun findFrom(data: Map<String, Any?>, names: List<String>): Any? {
		return names.throwIfNullOrEmpty2 {
			DataKernelException("Names cannot be null or empty.")
		}.fold(data) { from: Any?, name: String ->
			when (from) {
				is List<*> -> from.map { this.findFrom(it, name) }
				else -> this.findFrom(from, name)
			}
		}
	}

	private fun findFromCurrent(names: List<String>): Any? {
		return this.currentData?.let { this.findFrom(this.currentData, names) }
	}

	private fun findFromPrevious(names: List<String>): Any? {
		return this.previousData?.let { this.findFrom(this.previousData, names) }
	}

	override fun find(name: String): Any? {
		if (name.startsWith(FROM_PREVIOUS_PREFIX)) {
			return name.split(".")
				.handTo { it.slice(1..it.size) }
				.throwIfNullOrEmpty2 {
					return DataKernelException("Variable name[${name}] not declared.")
				}.handTo { names -> this.findFromPrevious(names as List<String>) }
		} else {
			if (map.containsKey(name)) {
				return map[name]
			}

			// split name with "."
			val names = name.split(".")
			if (names.size == 1) {
				// cannot be split, try to retrieve from current data
				return this.findFromCurrent(names)
			}

			// check if first part is existing in map
			if (map.containsKey(names[0])) {
				// true, try to retrieve from map
				return this.findFrom(map, names)
			}

			// still not in map, try to retrieve from current data
			return this.findFromCurrent(names)
		}
	}

	override fun put(name: String, value: Any?): PipelineVariables {
		super.put(name, value)
		return this
	}

	/**
	 * copy to a new variables,
	 * 1. for internal variables, not deep copy,
	 * 2. for previous/current, copy reference only.
	 */
	override fun copy(): PipelineVariables {
		return PipelineVariables(
			previousData = this.previousData, currentData = this.currentData
		).also { copied -> copied.map.putAll(this.map) }
	}
}