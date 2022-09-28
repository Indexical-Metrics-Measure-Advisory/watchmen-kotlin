package com.matrdata.watchmen.data.kernel.utils

import com.matrdata.watchmen.storage.SnowflakeGenerator

private fun askSnowflakeGenerator(): SnowflakeGenerator {
	return SnowflakeGenerator.INSTANCE
}

fun askNextId(): Long = askSnowflakeGenerator().nextId()

fun askNextIdAsStr(): String = askSnowflakeGenerator().nextIdAsStr()