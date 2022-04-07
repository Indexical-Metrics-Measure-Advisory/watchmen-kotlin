package com.matrdata.model.system

import com.matrdata.model.base.*
import java.util.*

enum class ExternalWriterType(val code: String) {
	STANDARD_WRITER("standard-writer"),
	ELASTIC_SEARCH_WRITER("elastic-search-writer")
}

class ExternalWriter(
	var writerId: ExternalWriterId? = null,
	var writerCode: String? = null,
	var type: ExternalWriterType? = ExternalWriterType.STANDARD_WRITER,
	// personal access token
	var pat: String? = null,
	var url: String? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : TenantBasedTuple, OptimisticLock