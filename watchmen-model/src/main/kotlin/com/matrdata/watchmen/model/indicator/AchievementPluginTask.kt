package com.matrdata.watchmen.model.indicator

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class AchievementPluginTaskStatus(val code: String) {
	SUBMITTED("submitted"),
	SENT("sent"),
	SUCCESS("success"),
	FAILED("failed")
}

data class AchievementPluginTask(
	var achievementTaskId: AchievementPluginTaskId? = null,
	var achievementId: AchievementId? = null,
	var pluginId: PluginId? = null,
	var status: AchievementPluginTaskStatus? = null,
	var url: String? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple
