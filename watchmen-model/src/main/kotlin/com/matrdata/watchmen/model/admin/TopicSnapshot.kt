package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.*
import java.time.LocalDate
import java.time.LocalDateTime

enum class TopicSnapshotFrequency(val code: String) {
	DAILY("daily"),
	WEEKLY("weekly"),
	MONTHLY("monthly")
}

data class TopicSnapshotScheduler(
	var schedulerId: TopicSnapshotSchedulerId? = null,
	var topicId: TopicId? = null,
	var targetTopicName: String? = null,
	var targetTopicId: TopicId? = null,
	var pipelineId: PipelineId? = null,
	var frequency: TopicSnapshotFrequency = TopicSnapshotFrequency.DAILY,
	var filter: Joint? = null,
	// only for weekly
	var weekday: String? = null,
	// only for monthly
	var day: String? = null,
	var hour: Int? = null,
	var minute: Int? = null,
	var enabled: Boolean? = true,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock

enum class TopicSnapshotJobLockStatus(val code: String) {
	READY("ready"),
	FAILED("fail"),
	SUCCESS("success")
}

data class TopicSnapshotJobLock(
	var lockId: TopicSnapshotJobLockId? = null,
	var schedulerId: TopicSnapshotSchedulerId? = null,
	var tenantId: TenantId? = null,
	var frequency: TopicSnapshotFrequency? = null,
	var processDate: LocalDate? = null,
	var rowCount: Int? = 0,
	var status: TopicSnapshotJobLockStatus? = null,
	var userId: UserId? = null,
	var createdAt: LocalDateTime? = null
) : Storable

