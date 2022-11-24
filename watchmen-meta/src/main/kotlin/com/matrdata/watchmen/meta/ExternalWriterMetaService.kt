package com.matrdata.watchmen.meta

import com.matrdata.watchmen.model.common.ExternalWriterId
import com.matrdata.watchmen.model.system.ExternalWriter

class ExternalWriterMetaService {
	fun findById(writerId: ExternalWriterId): ExternalWriter {
		TODO("Not yet implemented")
	}
}

fun askExternalWriterService(): ExternalWriterMetaService {
	return ExternalWriterMetaService()
}
