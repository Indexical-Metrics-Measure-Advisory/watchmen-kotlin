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
	override var tenantId: TenantId?,
	override var version: Int?,
	override var createdAt: LocalDateTime?,
	override var createdBy: UserId?,
	override var lastModifiedAt: LocalDateTime?,
	override var lastModifiedBy: UserId?
) : TenantBasedTuple, OptimisticLock
