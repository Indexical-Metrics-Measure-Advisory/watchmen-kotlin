package com.matrdata.watchmen.model.dqc

import com.matrdata.watchmen.model.admin.*
import com.matrdata.watchmen.model.common.*

fun createInsertOrMergeDetectedRuleToDaily(topicRuleResult: Topic, topicDaily: Topic): InsertOrMergeRowAction {
	return InsertOrMergeRowAction(
		actionId = "dqcp-a-1", topicId = topicDaily.topicId,
		mapping = listOf(
			MappingFactor(
				source = ParameterUtils.buildTopicFactorParameter(topicRuleResult, "ruleCode"),
				factorId = TopicUtils.findFactorByName(topicDaily, "ruleCode")!!.factorId
			),
			MappingFactor(
				source = ParameterUtils.buildTopicFactorParameter(topicRuleResult, "topicId"),
				factorId = TopicUtils.findFactorByName(topicDaily, "topicId")!!.factorId
			),
			MappingFactor(
				source = ParameterUtils.buildTopicFactorParameter(topicRuleResult, "factorId"),
				factorId = TopicUtils.findFactorByName(topicDaily, "factorId")!!.factorId
			),
			MappingFactor(
				source = ParameterUtils.buildSingleArgumentComputedParameter(
					topicRuleResult, "processDate", ParameterComputeType.YEAR_OF
				),
				factorId = TopicUtils.findFactorByName(topicDaily, "year")!!.factorId
			),
			MappingFactor(
				source = ParameterUtils.buildSingleArgumentComputedParameter(
					topicRuleResult, "processDate", ParameterComputeType.MONTH_OF
				),
				factorId = TopicUtils.findFactorByName(topicDaily, "month")!!.factorId
			),
			MappingFactor(
				source = ParameterUtils.buildSingleArgumentComputedParameter(
					topicRuleResult, "processDate", ParameterComputeType.DAY_OF_MONTH
				),
				factorId = TopicUtils.findFactorByName(topicDaily, "day")!!.factorId
			),
			MappingFactor(
				source = ParameterUtils.buildTopicFactorParameter(topicRuleResult, "processDate"),
				factorId = TopicUtils.findFactorByName(topicDaily, "processDate")!!.factorId
			),
			MappingFactor(
				source = ComputedParameter(
					parameters = listOf(
						ConstantParameter(
							conditional = true,
							on = Joint(
								jointType = ParameterJointType.AND,
								filters = listOf(
									DualExpression(
										left = ParameterUtils.buildTopicFactorParameter(
											topicRuleResult, "detected"
										),
										operator = DualParameterExpressionOperator.EQUALS,
										right = ConstantParameter(value = "true")
									)
								)
							),
							value = "1"
						),
						ConstantParameter(value = "0")
					)
				),
				factorId = TopicUtils.findFactorByName(topicDaily, "count")!!.factorId
			)
		),
		by = Joint(
			jointType = ParameterJointType.AND,
			filters = listOf(
				DualExpression(
					left = ParameterUtils.buildTopicFactorParameter(topicRuleResult, "ruleCode"),
					operator = DualParameterExpressionOperator.EQUALS,
					right = ParameterUtils.buildTopicFactorParameter(topicDaily, "ruleCode")
				),
				DualExpression(
					left = ParameterUtils.buildTopicFactorParameter(topicRuleResult, "topicId"),
					operator = DualParameterExpressionOperator.EQUALS,
					right = ParameterUtils.buildTopicFactorParameter(topicDaily, "topicId")
				),
				DualExpression(
					left = ParameterUtils.buildTopicFactorParameter(topicRuleResult, "factorId"),
					operator = DualParameterExpressionOperator.EQUALS,
					right = ParameterUtils.buildTopicFactorParameter(topicDaily, "factorId")
				),
				DualExpression(
					left = ParameterUtils.buildSingleArgumentComputedParameter(
						topicRuleResult, "processDate", ParameterComputeType.YEAR_OF
					),
					operator = DualParameterExpressionOperator.EQUALS,
					right = ParameterUtils.buildTopicFactorParameter(topicDaily, "year")
				),
				DualExpression(
					left = ParameterUtils.buildSingleArgumentComputedParameter(
						topicRuleResult, "processDate", ParameterComputeType.MONTH_OF
					),
					operator = DualParameterExpressionOperator.EQUALS,
					right = ParameterUtils.buildTopicFactorParameter(topicDaily, "month")
				),
				DualExpression(
					left = ParameterUtils.buildSingleArgumentComputedParameter(
						topicRuleResult, "processDate", ParameterComputeType.DAY_OF_MONTH
					),
					operator = DualParameterExpressionOperator.EQUALS,
					right = ParameterUtils.buildTopicFactorParameter(topicDaily, "day")
				)
			)
		)
	)
}

fun askDQCPipelines(topics: List<Topic>): List<Pipeline> {
	// define all dqc pipelines
	val topicRuleResult: Topic = TopicUtils.findTopicByName(topics, DQC_RAW_RULE_RESULT.name!!)!!
	val topicDaily: Topic = TopicUtils.findTopicByName(topics, DQC_RULE_DAILY.name!!)!!

	return listOf(
		Pipeline(
			name = "DQC Pipeline", topicId = topicRuleResult.topicId,
			type = PipelineTriggerType.INSERT_OR_MERGE,
			stages = listOf(
				PipelineStage(
					stageId = "dqcp-s-1", name = "DQC Pipeline Stage 1",
					units = listOf(
						PipelineUnit(
							unitId = "dqcp-u-1", name = "DQC Pipeline Unit 1",
							`do` = listOf(createInsertOrMergeDetectedRuleToDaily(topicRuleResult, topicDaily))
						)
					)
				)
			),
			enabled = true, validated = true
		)
	)
}