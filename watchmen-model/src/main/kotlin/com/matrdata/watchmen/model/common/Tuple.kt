package com.matrdata.watchmen.model.common

interface Tuple : Auditable

interface TenantBasedTuple : Tuple {
	var tenantId: TenantId?
}

interface UserBasedTuple : TenantBasedTuple {
	var userId: UserId?
}