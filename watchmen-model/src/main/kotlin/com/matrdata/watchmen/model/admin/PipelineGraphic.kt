package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class TopicRect(
	var coordinate: GraphicPosition? = null,
	var frame: GraphicRect? = null,
	var name: GraphicPosition? = null
) : DataModel

data class TopicGraphic(
	var topicId: TopicId? = null,
	var rect: TopicRect? = null,
) : DataModel

data class PipelineGraphic(
	var pipelineGraphId: PipelineGraphicId? = null,
	var name: String? = null,
	var topics: List<TopicGraphic>? = null,
	override var userId: UserId? = null,
	override var tenantId: TenantId? = null,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : UserBasedTuple
