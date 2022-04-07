package com.matrdata.model.analysis

import com.matrdata.model.admin.FactorType
import com.matrdata.model.base.*
import java.util.*

class FactorIndex(
	var factorIndexId: FactorIndexId? = null,
	var factorId: FactorId? = null,
	var factorType: FactorType? = null,
	var factorName: String? = null,
	var factorLabel: String? = null,
	var factorDescription: String? = null,
	var topicId: TopicId? = null,
	var topicName: String? = null,
	var tenantId: TenantId? = null,
	var createdAt: Date? = null,
	var lastModifiedAt: Date? = null
) : Storable