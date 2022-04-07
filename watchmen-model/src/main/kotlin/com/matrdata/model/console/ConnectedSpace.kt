package com.matrdata.model.console

import com.matrdata.model.base.*
import java.util.*

class ConnectedSpace(
	var connectId: ConnectedSpaceId? = null,
	var spaceId: SpaceId? = null,
	var name: StandardTupleName? = null,
	var isTemplate: Boolean? = false,
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override val lastVisitTime: Date? = null,
	override var tenantId: TenantId? = null,
	override var userId: UserId? = null
) : UserBasedTuple, Auditable, LastVisit