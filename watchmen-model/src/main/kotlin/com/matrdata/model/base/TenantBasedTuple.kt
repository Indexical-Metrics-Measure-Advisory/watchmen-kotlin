package com.matrdata.model.base

interface TenantBasedTuple : Auditable {
    var tenantId: TenantId?
}
