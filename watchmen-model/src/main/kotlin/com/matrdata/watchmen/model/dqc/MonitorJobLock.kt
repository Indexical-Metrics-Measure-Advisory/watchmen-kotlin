package com.matrdata.watchmen.model.dqc

import com.matrdata.watchmen.model.common.*
import java.time.LocalDate
import java.time.LocalDateTime

enum class MonitorJobLockStatus(val code: String) {
	READY("ready"),
	FAILED("fail"),
	SUCCESS("success")
}

data class MonitorJobLock(
	var lockId: MonitorJobLockId? = null,
	var tenantId: TenantId? = null,
	var topicId: TopicId? = null,
	var frequency: MonitorRuleStatisticalInterval? = null,
	var processDate: LocalDate? = null,
	var status: MonitorJobLockStatus? = null,
	var userId: UserId? = null,
	var createdAt: LocalDateTime? = null,
) : Storable