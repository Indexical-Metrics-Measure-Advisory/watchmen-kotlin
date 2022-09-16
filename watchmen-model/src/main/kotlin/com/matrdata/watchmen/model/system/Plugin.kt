package com.matrdata.watchmen.model.system

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class PluginType(val code: String) {
	@Suppress("SpellCheckingInspection")
	STREAMLIT("streamlit"),
	JUPYTER("jupyter")
}

enum class PluginApplyTo(val code: String) {
	ACHIEVEMENT("achievement")
}

data class Plugin(
	var pluginId: PluginId? = null,
	var pluginCode: String? = null,
	var name: String? = null,
	var type: PluginType? = null,
	var applyTo: PluginApplyTo? = null,
	// value is parameter name
	var params: List<String>? = null,
	// value is result name
	var results: List<String>? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock
