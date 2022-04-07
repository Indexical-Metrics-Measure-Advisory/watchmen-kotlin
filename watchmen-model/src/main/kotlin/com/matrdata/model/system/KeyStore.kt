package com.matrdata.model.system

import com.matrdata.model.base.Storable
import com.matrdata.model.base.TenantId
import com.matrdata.model.base.UserId
import java.util.*

class KeyStore(
	var tenantId: TenantId? = null,
	var keyType: String? = null,
	var params: Map<String, String>? = null,
	var createdAt: Date? = null,
	var createdBy: UserId? = null
) : Storable