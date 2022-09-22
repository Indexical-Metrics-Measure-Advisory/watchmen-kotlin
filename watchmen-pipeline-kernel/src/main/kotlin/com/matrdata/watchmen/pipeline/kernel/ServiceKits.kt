package com.matrdata.watchmen.pipeline.kernel

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.meta.PipelineMetaService
import com.matrdata.watchmen.data.kernel.meta.TopicMetaService
import com.matrdata.watchmen.storage.SnowflakeGenerator

fun askSnowflakeGenerator(): SnowflakeGenerator {
	return SnowflakeGenerator.INSTANCE
}

fun askTopicMetaService(principal: Principal): TopicMetaService {
	return TopicMetaService(principal)
}

fun askPipelineMetaService(principal: Principal): PipelineMetaService {
	return PipelineMetaService(principal)
}