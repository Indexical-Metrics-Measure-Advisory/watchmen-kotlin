package com.matrdata.model.admin

import com.matrdata.model.base.*
import com.matrdata.model.common.ParameterJoint
import java.util.*

class SpaceFilter(
    var topicId: TopicId? = null,
    var enabled: Boolean? = false,
    var joint: ParameterJoint? = null
)

class Space(
    var spaceId: SpaceId? = null,
    var name: StandardTupleName? = null,
    var description: String? = null,
    var topicIds: List<TopicId>? = null,
    var groupIds: List<UserGroupId>? = null,
    var filters: List<SpaceFilter>? = null,
    override var createdAt: Date? = null,
    override var createdBy: UserId? = null,
    override var lastModifiedAt: Date? = null,
    override var lastModifiedBy: UserId? = null,
    override var version: Int? = null,
    override var tenantId: TenantId? = null
) : TenantBasedTuple, OptimisticLock