package com.matrdata.model.console

import com.matrdata.model.base.*
import com.matrdata.model.common.Parameter
import com.matrdata.model.common.ParameterJoint
import java.util.*

enum class SubjectColumnArithmetic(val code: String) {
	NONE("none"),
	COUNT("count"),
	SUMMARY("sum"),
	AVERAGE("avg"),
	MAXIMUM("max"),
	MINIMUM("min")
}

class SubjectDatasetColumn(
	var columnId: SubjectDatasetColumnId? = null,
	var parameter: Parameter? = null,
	var alias: String? = null,
	var arithmetic: SubjectColumnArithmetic? = null
)

enum class SubjectJoinType(val code: String) {
	LEFT("left"),
	RIGHT("right"),
	INNER("inner")
}

class SubjectDatasetJoin(
	var topicId: TopicId? = null,
	var factorId: FactorId? = null,
	var secondaryTopicId: TopicId? = null,
	var secondaryFactorId: FactorId? = null,
	var type: SubjectJoinType? = SubjectJoinType.INNER
)

class SubjectDataset(
	columns: List<SubjectDatasetColumn>? = mutableListOf(),
	joins: List<SubjectDatasetJoin> = mutableListOf(),
	filters: ParameterJoint? = null
)

class Subject(
	var subjectId: SubjectId? = null,
	var name: StandardTupleName? = null,
	var connectId: ConnectedSpaceId? = null,
	var autoRefreshInterval: Int? = 0,
	var dataset: SubjectDataset? = null,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override val lastVisitTime: Date? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple, Auditable, LastVisit