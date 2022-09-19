package com.matrdata.watchmen.model.system

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class ExternalWriterType(val code: String) {
	STANDARD_WRITER("standard-writer"),
	ELASTIC_SEARCH_WRITER("elastic-search-writer")
}

data class ExternalWriter(
	var writerId: ExternalWriterId? = null,
	var writerCode: String? = null,
	var name: String? = null,
	var type: ExternalWriterType = ExternalWriterType.STANDARD_WRITER,
	// personal access token
	var pat: String? = null,
	var url: String? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock
