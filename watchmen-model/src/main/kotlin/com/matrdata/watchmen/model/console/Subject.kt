package com.matrdata.watchmen.model.console

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class SubjectJoinType(val code: String) {
	LEFT("left"),
	RIGHT("right"),
	INNER("inner")
}

data class SubjectDatasetJoin(
	var topicId: TopicId? = null,
	var factorId: FactorId? = null,
	var secondaryTopicId: TopicId? = null,
	var secondaryFactorId: FactorId? = null,
	var type: SubjectJoinType? = SubjectJoinType.INNER
) : DataModel

enum class SubjectColumnArithmetic(val code: String) {
	NONE("none"),
	COUNT("count"),
	SUMMARY("sum"),
	AVERAGE("avg"),
	MAXIMUM("max"),
	MINIMUM("min")
}

enum class SubjectColumnAlignment(val code: String) {
	LEFT("left"),
	CENTER("center"),
	RIGHT("right")
}

enum class SubjectColumnFormat(val code: String) {
	USE_GROUP("#,##0"),
	USE_GROUP_1("#,##0.0"),
	USE_GROUP_2("#,##0.00"),
	USE_GROUP_3("#,##0.000"),
	USE_GROUP_4("#,##0.0000"),
	USE_GROUP_5("#,##0.00000"),
	USE_GROUP_6("#,##0.000000")
}

data class SubjectDataSetColumnRenderer(
	var alignment: SubjectColumnAlignment? = null,
	var format: SubjectColumnFormat? = null,
	var highlightNegative: Boolean? = false,
) : DataModel

data class SubjectDatasetColumn(
	var columnId: SubjectDatasetColumnId? = null,
	var parameter: Parameter? = null,
	var alias: String? = null,
	var arithmetic: SubjectColumnArithmetic? = null,
	var renderer: SubjectDataSetColumnRenderer? = null,
) : DataModel

data class SubjectDataset(
	var columns: List<SubjectDatasetColumn>? = null,
	var joins: List<SubjectDatasetJoin>? = null,
	var filters: Joint? = null
) : DataModel

data class Subject(
	var subjectId: SubjectId? = null,
	var name: String? = null,
	var connectIdval: ConnectedSpaceId? = null,
	var autoRefreshInterval: Int? = null,
	var dataset: SubjectDataset? = null,
	override var lastVisitTime: LocalDateTime? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple, Auditable, LastVisit

data class SubjectDatasetPage(
	var meta: List<String>? = null,
	var data: Dataset,
)

enum class SubjectDatasetCriteriaIndicatorArithmetic(val code: String) {
	NONE("none"),
	COUNT("count"),
	SUMMARY("sum"),
	AVERAGE("avg"),
	MAXIMUM("max"),
	MINIMUM("min")
}

data class SubjectDatasetCriteriaIndicator(
	var name: String? = null,
	var arithmetic: SubjectDatasetCriteriaIndicatorArithmetic? = null,
	var aliasval: String? = null,
)

data class SubjectDatasetCriteria(
	// use one of subject id or name
	var subjectId: SubjectId? = null,
	var subjectName: String? = null,
	var indicators: List<SubjectDatasetCriteriaIndicator>? = null,
	var conditions: List<Condition>? = null,
	override var pageNumber: Int = 1,
	override var pageSize: Int = 100,
) : Pageable