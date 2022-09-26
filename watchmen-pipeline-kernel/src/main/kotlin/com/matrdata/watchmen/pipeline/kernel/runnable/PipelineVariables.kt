package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.model.admin.Topic
import com.matrdata.watchmen.pipeline.kernel.PipelineKernelException

class PipelineVariables(
	private val previousData: Map<String, Any?>?, private val currentData: Map<String, Any?>?,
	private val topic: Topic
) {
	private val map: MutableMap<String, Any?> = mutableMapOf()

	private fun findFrom(from: Any?, name: String): Any? {
		return when (from) {
			null -> null
			is Map<*, *> -> from[name]
			// TODO how to get property value from non-map object
			else -> throw PipelineKernelException("Only map is supported in pipeline variables.")
		}
	}

	private fun findFrom(data: Map<String, Any?>, names: List<String>): Any? {
		return names.fold(data) { from: Any?, name: String ->
			when (from) {
				is List<*> -> from.map { this.findFrom(it, name) }
				else -> this.findFrom(from, name)
			}
		}
	}

	fun findFromCurrent(names: List<String>): Any? {
		return this.currentData?.let { this.findFrom(this.currentData, names) }
	}

	fun find(name: String): Any? {
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

	/**
	 * put value into variables.
	 * note even name has ".", still use it as a whole part as key.
	 * which means, write internal field/property of existing data would <b>NEVER HAPPEN</b>.
	 */
	fun put(name: String, value: Any?): PipelineVariables {
		map[name] = value
		return this
	}

	fun copy(): PipelineVariables {
		return PipelineVariables(
			previousData = this.previousData, currentData = this.currentData, topic = this.topic
		).also { copied -> copied.map.putAll(this.map) }
	}
}