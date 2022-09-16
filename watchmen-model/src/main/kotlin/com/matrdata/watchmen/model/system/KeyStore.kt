package com.matrdata.watchmen.model.system

import com.matrdata.watchmen.model.common.Storable
import com.matrdata.watchmen.model.common.TenantId
import com.matrdata.watchmen.model.common.UserId
import java.time.LocalDateTime

data class KeyStore(
	var tenantId: TenantId? = null,
	var keyType: String? = null,
	var params: Map<String, String>? = null,
	var createdAt: LocalDateTime? = null,
	var createdBy: UserId? = null
) : Storable