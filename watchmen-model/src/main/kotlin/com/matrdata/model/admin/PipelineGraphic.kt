package com.matrdata.model.admin

import com.matrdata.model.base.*
import com.matrdata.model.common.GraphicPosition
import com.matrdata.model.common.GraphicRect
import java.util.*

class TopicRect(
	var coordinate: GraphicPosition? = null,
	var frame: GraphicRect? = null,
	var name: GraphicPosition? = null
)

class TopicGraphic(
	var topicId: TopicId? = null,
	var rect: TopicRect? = null
)

class PipelineGraphic(
	var pipelineGraphId: PipelineGraphicId? = null,
	var name: StandardTupleName? = null,
	var topics: List<TopicGraphic>? = null,
	var createdAt: Date? = null,
	var lastModifiedAt: Date? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple