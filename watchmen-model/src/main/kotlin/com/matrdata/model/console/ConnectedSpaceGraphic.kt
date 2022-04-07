package com.matrdata.model.console

import com.matrdata.model.base.*
import com.matrdata.model.common.GraphicRect

class TopicGraphic(
	var topicId: TopicId? = null,
	var rect: GraphicRect? = null
)

class SubjectGraphic(
	var subjectId: SubjectId? = null,
	var rect: GraphicRect? = null
)

class ConnectedSpaceGraphic(
	var connectId: ConnectedSpaceId? = null,
	var topics: List<TopicGraphic>? = null,
	var subjects: List<SubjectGraphic>? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple