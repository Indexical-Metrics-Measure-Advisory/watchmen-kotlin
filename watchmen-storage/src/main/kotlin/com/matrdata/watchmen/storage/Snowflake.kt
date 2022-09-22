package com.matrdata.watchmen.storage

class SnowflakeGenerator {
	companion object {
		// TODO not yet implemented
		val INSTANCE: SnowflakeGenerator = SnowflakeGenerator()
	}

	fun nextId(): Long {
		TODO("Not yet implemented")
	}

	fun nextIdAsStr(): String {
		return nextId().toString()
	}
}