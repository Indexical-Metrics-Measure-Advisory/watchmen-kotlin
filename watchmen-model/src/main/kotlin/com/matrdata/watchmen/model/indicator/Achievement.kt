package com.matrdata.watchmen.model.indicator

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

sealed interface AchievementIndicator : DataModel {
	var indicatorId: IndicatorId?
	var name: String?
	var aggregateArithmetic: IndicatorAggregateArithmetic?
	var formula: String?
	var includeInFinalScore: Boolean?
	var criteria: List<IndicatorCriteria>?
	var variableName: String?
}

val MANUAL_COMPUTE_ACHIEVEMENT_INDICATOR_ID = "-1"

/**
 * for manual compute indicator,
 * 1. indicatorId fixed as {@link MANUAL_COMPUTE_ACHIEVEMENT_INDICATOR_ID},
 * 2. aggregateArithmetics fixed as {@link IndicatorAggregateArithmetic#MAX}, will be ignored anyway in runtime
 * 3. criteria fixed as zero length array, will be ignored anyway in runtime
 */
data class ManualComputeAchievementIndicator(
	override var name: String? = null,
	override var formula: String? = null,
	override var includeInFinalScore: Boolean? = true,
	override var criteria: List<IndicatorCriteria>? = null,
	override var variableName: String? = null
) : AchievementIndicator {
	companion object {
		val MANUAL_COMPUTE_ACHIEVEMENT_INDICATOR_ID = "-1"
	}

	@Suppress("UNUSED_PARAMETER")
	override var indicatorId: IndicatorId?
		get() = MANUAL_COMPUTE_ACHIEVEMENT_INDICATOR_ID
		set(value) {}

	@Suppress("UNUSED_PARAMETER")
	override var aggregateArithmetic: IndicatorAggregateArithmetic?
		get() = IndicatorAggregateArithmetic.MAX
		set(value) {}
}


enum class AchievementTimeRangeType(val code: String) {
	YEAR("year"),
	MONTH("month")
}

data class Achievement(
	var achievementId: AchievementId? = null,
	var name: String? = null,
	var description: String? = null,
	var timeRangeType: AchievementTimeRangeType? = AchievementTimeRangeType.YEAR,
	var timeRangeYear: String? = null,
	var timeRangeMonth: String? = null,
	var compareWithPreviousTimeRange: Boolean? = false,
	var finalScoreIsRatio: Boolean? = false,
	var indicators: List<AchievementIndicator>? = null,
	var pluginIds: List<PluginId>? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock