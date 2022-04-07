package com.matrdata.model.dqc

import com.matrdata.model.base.*
import java.util.*

enum class MonitorJobLockStatus(val code: String) {
	READY("ready"),
	FAILED("fail"),
	SUCCESS("success")
}

class MonitorJobLock(
	var lockId: MonitorJobLockId?=null,
	var tenantId : TenantId?=null,
	var topicId: TopicId?=null,
	var frequency : MonitorRuleStatisticalInterval?=null,
	var processDate: Date?=null,
	var status : MonitorJobLockStatus?=null,
	var userId: UserId?=null,
	var createdAt: Date?=null,
) : Storable