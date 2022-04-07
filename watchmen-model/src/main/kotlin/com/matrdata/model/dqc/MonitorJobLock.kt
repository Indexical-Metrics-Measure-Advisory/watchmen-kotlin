package com.matrdata.model.dqc

import com.matrdata.model.base.*
import java.util.*

enum class MonitorJobLockStatus(val code: String) {
	READY("ready"),
	FAILED("fail"),
	SUCCESS("success")
}

class MonitorJobLock(
	lockId: MonitorJobLockId?=null,
	tenantId : TenantId?=null,
	topicId: TopicId?=null,
	frequency : MonitorRuleStatisticalInterval?=null,
	processDate: Date?=null,
	status : MonitorJobLockStatus?=null,
	userId: UserId?=null,
	createdAt: Date?=null,
) : Storable