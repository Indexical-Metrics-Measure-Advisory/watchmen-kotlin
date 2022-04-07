package com.matrdata.model.system

import com.matrdata.model.base.PatId
import com.matrdata.model.base.TenantId
import com.matrdata.model.base.UserBasedTuple
import com.matrdata.model.base.UserId
import java.util.*

class Token(
	var accessToken: String? = null,
	var tokenType: String? = null,
	var role: String? = null,
	var tenantId: TenantId? = null
)


class PersonalAccessToken(
	var patId: PatId? = null,
	var token: String? = null,
	var username: String? = null,
	var note: String? = null,
	var expired: Date? = null,
	var permissions: List<Any>? = null,
	var createdAt: Date? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple