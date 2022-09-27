package com.matrdata.watchmen.pipeline.kernel.utils

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.data.kernel.meta.ExternalWriterMetaService
import com.matrdata.watchmen.model.common.ExternalWriterId
import com.matrdata.watchmen.model.system.ExternalWriter
import com.matrdata.watchmen.pipeline.kernel.PipelineKernelException
import com.matrdata.watchmen.utils.handTo
import com.matrdata.watchmen.utils.throwIfBlank2
import com.matrdata.watchmen.utils.throwIfNull2

private fun askExternalWriterService(): ExternalWriterMetaService {
	return ExternalWriterMetaService()
}

fun askWriterById(writerId: ExternalWriterId?, principal: Principal): ExternalWriter {
	return writerId
		.throwIfBlank2 { PipelineKernelException("External writer id cannot be null or blank.") }
		.handTo { id -> askExternalWriterService().findById(id) }
		.throwIfNull2 { PipelineKernelException("External writer[id=$writerId] not found.") }
		.takeUnless { writer -> writer.tenantId == principal.getTenantId() }
		// writer not belongs to given principal
		.throwIfNull2 { PipelineKernelException("External writer[id=$writerId] not found.") }
}
