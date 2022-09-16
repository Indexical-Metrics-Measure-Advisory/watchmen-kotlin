package com.matrdata.watchmen.model.system

import com.matrdata.watchmen.model.common.PatId
import com.matrdata.watchmen.model.common.TenantId
import com.matrdata.watchmen.model.common.UserBasedTuple
import com.matrdata.watchmen.model.common.UserId
import java.time.LocalDateTime

data class Token(
	val accessToken: String,
	val tokenType: String,
	val role: String,
	val tenantId: TenantId
)

data class PersonalAccessToken(
	var patId: PatId? = null,
	var token: String? = null,
	var username: String? = null,
	var note: String? = null,
	var expired: LocalDateTime? = null,
	var permissions: List<String>? = null,
	override var userId: UserId?,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple
