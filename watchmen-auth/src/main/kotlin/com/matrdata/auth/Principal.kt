package com.matrdata.auth

import com.matrdata.model.admin.UserRole
import com.matrdata.model.base.TenantId
import com.matrdata.model.base.UserId

class Principal(
    val tenantId: TenantId,
    val userId: UserId,
    val userName: String,
    val role: UserRole
) {
    fun isAdmin(): Boolean {
        return this.isTenantAdmin() || this.isSuperAdmin()
    }

    fun isTenantAdmin(): Boolean {
        return this.role == UserRole.ADMIN
    }

    fun isSuperAdmin(): Boolean {
        return this.role == UserRole.SUPER_ADMIN
    }
}