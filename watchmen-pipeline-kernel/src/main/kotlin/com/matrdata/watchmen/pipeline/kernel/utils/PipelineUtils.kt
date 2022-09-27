package com.matrdata.watchmen.pipeline.kernel.utils

import com.matrdata.watchmen.data.kernel.meta.PipelineMetaService
import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.common.TopicId

private fun askPipelineMetaService(): PipelineMetaService {
	return PipelineMetaService()
}

fun askPipelinesByTopicId(topicId: TopicId): List<Pipeline> {
	return askPipelineMetaService().findByTopicId(topicId)
}