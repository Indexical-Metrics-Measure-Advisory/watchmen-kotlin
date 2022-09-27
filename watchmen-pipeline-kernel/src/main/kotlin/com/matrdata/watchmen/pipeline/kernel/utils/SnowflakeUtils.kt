package com.matrdata.watchmen.pipeline.kernel.utils

import com.matrdata.watchmen.storage.SnowflakeGenerator

private fun askSnowflakeGenerator(): SnowflakeGenerator {
	return SnowflakeGenerator.INSTANCE
}

fun askNextIdAsStr(): String = askSnowflakeGenerator().nextIdAsStr()