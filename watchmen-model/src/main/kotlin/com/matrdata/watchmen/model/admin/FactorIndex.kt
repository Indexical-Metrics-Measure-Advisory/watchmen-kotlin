package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

data class FactorIndex(
	var factorIndexId: FactorIndexId? = null,
	var factorId: FactorId? = null,
	var factorType: FactorType? = null,
	var factorName: String? = null,
	var factorLabel: String? = null,
	var factorDescription: String? = null,
	var topicId: TopicId? = null,
	var topicName: String? = null,
	var tenantId: TenantId? = null,
	var createdAt: LocalDateTime? = null,
	var lastModifiedAt: LocalDateTime? = null,
) : Storable
	