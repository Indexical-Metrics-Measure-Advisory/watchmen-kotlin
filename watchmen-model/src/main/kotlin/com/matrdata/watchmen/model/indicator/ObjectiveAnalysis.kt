package com.matrdata.watchmen.model.indicator

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class ObjectiveAnalysisPerspectiveType(val code: String) {
	INSPECTION("inspection"),
	ACHIEVEMENT("achievement")
}

data class ObjectiveAnalysisPerspective(
	var perspectiveId: ObjectiveAnalysisPerspectiveId? = null,
	var description: String? = null,
	var type: ObjectiveAnalysisPerspectiveType? = ObjectiveAnalysisPerspectiveType.INSPECTION,
	var relationId: InspectionOrAchievementId? = null
) : DataModel

data class ObjectiveAnalysis(
	var analysisId: ObjectiveAnalysisId? = null,
	var title: String? = null,
	var description: String? = null,
	var perspectives: List<ObjectiveAnalysisPerspective>? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock
