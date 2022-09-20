package com.matrdata.watchmen.auth

import com.matrdata.watchmen.model.admin.User
import com.matrdata.watchmen.model.admin.UserRole
import com.matrdata.watchmen.model.common.TenantId
import com.matrdata.watchmen.model.common.UserId
import com.matrdata.watchmen.utils.assert

data class Principal(val user: User) {
	fun getTenantId(): TenantId {
		return this.user.tenantId!!
	}

	fun getUserId(): UserId {
		return this.user.userId!!
	}

	fun getUserName(): String {
		return this.user.name ?: "Anonymous User"
	}

	fun getUserRole(): UserRole {
		return this.user.role!!
	}

	fun isAdmin(): Boolean {
		return this.getUserRole().assert { this == UserRole.ADMIN || this == UserRole.SUPER_ADMIN }
	}

	fun isTenantAdmin(): Boolean {
		return this.getUserRole().assert { this == UserRole.ADMIN }
	}

	fun isSuperAdmin(): Boolean {
		return this.getUserRole().assert { this == UserRole.SUPER_ADMIN }
	}

	fun isConsole(): Boolean {
		return isAdmin().not()
	}
}