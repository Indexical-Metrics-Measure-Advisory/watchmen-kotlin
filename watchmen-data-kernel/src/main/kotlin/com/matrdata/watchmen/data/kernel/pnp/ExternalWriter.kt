package com.matrdata.watchmen.data.kernel.pnp

import com.matrdata.watchmen.utils.Slf4j.Companion.logger
import com.matrdata.watchmen.utils.throwIfNull

data class ExternalWriterParams(
	var pat: String?,
	var url: String?,
	var eventCode: String?,
	var currentData: Map<String, Any?>?,
	var previousData: Map<String, Any?>?,
	var variables: Map<String, Any?>
)

interface ExternalWriter {
	fun getCode(): String
	fun run(params: ExternalWriterParams): Boolean
}

typealias ExternalWriterBuild = (String) -> ExternalWriter

class ExternalWriterRegistry {
	companion object {
		val INSTANCE = ExternalWriterRegistry()
	}

	private val builders: MutableMap<String, ExternalWriterBuild> = mutableMapOf()

	fun register(code: String, build: ExternalWriterBuild): ExternalWriterBuild? {
		val original = this.builders[code]
		this.builders[code] = build
		if (original == null) {
			logger.warn("External writer[$code] is replaced.")
		} else {
			logger.info("External writer[$code] is registered.")
		}
		return original
	}

	fun isRegistered(code: String): Boolean = this.builders.containsKey(code)

	fun askWriter(code: String): ExternalWriter {
		return this.builders[code].throwIfNull { "Creator not found for external writer[$code]." }(code)
	}
}