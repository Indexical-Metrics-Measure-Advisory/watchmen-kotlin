package com.matrdata.watchmen.model.dqc

import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.DataModel
import com.matrdata.watchmen.model.common.FactorId
import com.matrdata.watchmen.model.common.TopicId
import java.time.LocalDateTime

/**
 * same structure with dqc_raw_rule_result
 */
data class MonitorRuleDetected(
	var ruleCode: MonitorRuleCode? = null,
	var topicId: TopicId? = null,
	var topicName: String? = null,
	var factorId: FactorId? = null,
	var factorName: String? = null,
	var detected: Boolean? = false,  // issue detected
	var severity: MonitorRuleSeverity? = null,
	var processDate: LocalDateTime? = null
) : DataModel

val DQC_RAW_RULE_RESULT = Topic(
	name = "dqc_raw_rule_result", kind = TopicKind.SYSTEM, type = TopicType.RAW,
	factors = listOf(
		Factor(
			factorId = "dra-f-1", name = "ruleCode", type = FactorType.TEXT,
			flatten = true, indexGroup = FactorIndexGroup.INDEX_1, precision = "50"
		),
		Factor(
			factorId = "dra-f-2", name = "topicId", type = FactorType.TEXT,
			flatten = true, indexGroup = FactorIndexGroup.INDEX_2, precision = "50"
		),
		Factor(factorId = "dra-f-3", name = "topicName", type = FactorType.TEXT),
		Factor(
			factorId = "dra-f-4", name = "factorId", type = FactorType.TEXT,
			flatten = true, indexGroup = FactorIndexGroup.INDEX_3, precision = "50"
		),
		Factor(factorId = "dra-f-5", name = "factorName", type = FactorType.TEXT),
		Factor(factorId = "dra-f-6", name = "detected", type = FactorType.BOOLEAN, flatten = true),
		Factor(factorId = "dra-f-7", name = "severity", type = FactorType.TEXT),
		/**
		 * the start day of date range,
		 * 1. sunday when weekly;
		 * 2. or 1st day when monthly
		 */
		Factor(
			factorId = "dra-f-8", name = "processDate", type = FactorType.DATE,
			flatten = true, indexGroup = FactorIndexGroup.INDEX_4
		)
	),
	description = "Topic data monitor by rules, raw topic."
)

val DQC_RULE_DAILY = Topic(
	name = "dqc_rule_daily", kind = TopicKind.SYSTEM, type = TopicType.DISTINCT,
	factors = listOf(
		Factor(factorId = "dra-f-1", name = "ruleCode", type = FactorType.TEXT, indexGroup = FactorIndexGroup.INDEX_1),
		Factor(factorId = "dra-f-2", name = "topicId", type = FactorType.TEXT, indexGroup = FactorIndexGroup.INDEX_2),
		Factor(factorId = "dra-f-3", name = "factorId", type = FactorType.TEXT, indexGroup = FactorIndexGroup.INDEX_3),
		Factor(factorId = "dra-f-4", name = "year", type = FactorType.YEAR, indexGroup = FactorIndexGroup.INDEX_4),
		Factor(factorId = "dra-f-5", name = "month", type = FactorType.MONTH, indexGroup = FactorIndexGroup.INDEX_5),
		Factor(
			factorId = "dra-f-6", name = "day", type = FactorType.DAY_OF_MONTH,
			indexGroup = FactorIndexGroup.INDEX_6
		),
		Factor(
			factorId = "dra-f-7", name = "processDate", type = FactorType.DATE,
			indexGroup = FactorIndexGroup.INDEX_7
		),
		Factor(factorId = "dra-f-8", name = "count', type = FactorType.UNSIGNED, precision = '10")
	),
	description = "Topic data monitor by rules, distinct topic."
)

fun askDQCTopics(): List<Topic> {
	// TODO define all dqc topics
	return listOf(DQC_RAW_RULE_RESULT, DQC_RULE_DAILY)
}