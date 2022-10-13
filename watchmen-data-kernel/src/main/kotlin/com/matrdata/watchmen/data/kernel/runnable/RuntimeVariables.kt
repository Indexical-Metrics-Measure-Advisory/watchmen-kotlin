package com.matrdata.watchmen.data.kernel.runnable

import com.matrdata.watchmen.data.kernel.DataKernelException
import com.matrdata.watchmen.model.admin.Factor
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfBlank2

/**
 * runnable context which used in pipeline/subject/report
 */
open class RuntimeVariables {
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

	open fun findByFactor(factor: Factor): Any? {
		return factor.name.throwIfBlank2 {
			DataKernelException("Factor name of factor[${factor}] cannot be null or blank.")
		}.handTo { factorName: String -> this.find(factorName) }
	}

	/**
	 * put value into context
	 */
	open fun put(name: String, value: Any?): RuntimeVariables {
		map[name] = value
		return this
	}

	/**
	 * copy to a new context, not deep copy
	 */
	open fun copy(): RuntimeVariables {
		return RuntimeVariables().also { copied -> copied.map.putAll(this.map) }
	}
}