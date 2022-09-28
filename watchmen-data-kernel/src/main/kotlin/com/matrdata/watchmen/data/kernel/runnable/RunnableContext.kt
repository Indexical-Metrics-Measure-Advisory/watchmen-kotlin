package com.matrdata.watchmen.data.kernel.runnable

import com.matrdata.watchmen.data.kernel.DataKernelException

/**
 * runnable context which used in pipeline/subject/report
 */
open class RunnableContext {
	protected val map: MutableMap<String, Any?> = mutableMapOf()

	/**
	 * find value by given name
	 */
	protected fun findFrom(from: Any?, name: String): Any? {
		return when (from) {
			null -> null
			is Map<*, *> -> from[name]
			// TODO how to get property value from non-map object
			else -> throw DataKernelException("Only value retrieving from map is supported.")
		}
	}

	open fun find(name: String): Any? {
		return if (map.containsKey(name)) map[name] else null
	}

	/**
	 * put value into context
	 */
	open fun put(name: String, value: Any?): RunnableContext {
		map[name] = value
		return this
	}

	/**
	 * copy to a new context, not deep copy
	 */
	open fun copy(): RunnableContext {
		return RunnableContext().also { copied -> copied.map.putAll(this.map) }
	}
}