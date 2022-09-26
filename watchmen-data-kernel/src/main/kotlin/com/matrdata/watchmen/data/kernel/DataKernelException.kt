package com.matrdata.watchmen.data.kernel

class DataKernelException(message: String?, cause: Throwable?) : RuntimeException(message, cause) {
	constructor(message: String?) : this(message, null)
}
