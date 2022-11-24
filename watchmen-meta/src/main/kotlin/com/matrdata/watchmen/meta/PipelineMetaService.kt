package com.matrdata.watchmen.meta

import com.matrdata.watchmen.model.admin.Pipeline
import com.matrdata.watchmen.model.common.PipelineId

class PipelineMetaService {
	fun findById(pipelineId: PipelineId): Pipeline {
		TODO("Not yet implemented")
	}
}

fun askPipelineMetaService(): PipelineMetaService {
	return PipelineMetaService()
}