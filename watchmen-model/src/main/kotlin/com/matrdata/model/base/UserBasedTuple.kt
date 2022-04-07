package com.matrdata.model.base

interface UserBasedTuple : Storable {
	var tenantId: TenantId?
	var userId: UserId?
}