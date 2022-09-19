package com.matrdata.watchmen.model.console

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class TopicGraphic(
	var topicId: TopicId? = null,
	var rect: GraphicRect? = null
) : DataModel

data class SubjectGraphic(
	var subjectId: SubjectId? = null,
	var rect: GraphicRect? = null,
) : DataModel

data class ConnectedSpaceGraphic(
	var connectId: ConnectedSpaceId? = null,
	var topics: List<TopicGraphic>? = null,
	var subjects: List<SubjectGraphic>? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple