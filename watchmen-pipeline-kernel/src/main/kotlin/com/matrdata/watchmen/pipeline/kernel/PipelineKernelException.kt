package com.matrdata.watchmen.pipeline.kernel

class PipelineKernelException(message: String?, cause: Throwable?) : RuntimeException(message, cause) {
	constructor(message: String?) : this(message, null)
}
