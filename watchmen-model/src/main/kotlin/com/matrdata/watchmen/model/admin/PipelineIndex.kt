package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class PipelineIndexRefType(val code: String) {
	DIRECT("direct"),
	COMPUTED("computed")
}

data class PipelineIndex(
	var pipelineIndexId: PipelineIndexId? = null,
	var pipelineId: PipelineId? = null,
	var pipelineName: String? = null,
	var stageId: PipelineStageId? = null,
	var stageName: String? = null,
	var unitId: PipelineUnitId? = null,
	var unitName: String? = null,
	var actionId: PipelineActionId? = null,
	var mappingToTopicId: TopicId? = null,
	var mappingToFactorId: FactorId? = null,
	var sourceFromTopicId: TopicId? = null,
	var sourceFromFactorId: FactorId? = null,
	var refType: PipelineIndexRefType? = null,
	var tenantId: TenantId? = null,
	var createdAt: LocalDateTime? = null,
	var lastModifiedAt: LocalDateTime? = null,
) : Storable

