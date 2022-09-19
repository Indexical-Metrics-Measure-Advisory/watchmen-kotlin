package com.matrdata.watchmen.model.dqc

import com.matrdata.watchmen.model.common.DataModel
import com.matrdata.watchmen.model.common.FactorId
import com.matrdata.watchmen.model.common.TopicId
import java.time.LocalDateTime

data class MonitorRuleLog(
	var ruleCode: MonitorRuleCode? = null,
	var topicId: TopicId? = null,
	var factorId: FactorId? = null,
	var count: Int? = null,
	var lastOccurredTime: LocalDateTime? = null,
) : DataModel

data class MonitorRuleLogCriteria(
	var startDate: String? = null,
	var endDate: String? = null,
	var ruleCode: MonitorRuleCode? = null,
	var topicId: TopicId? = null,
	var factorId: FactorId? = null,
) : DataModel