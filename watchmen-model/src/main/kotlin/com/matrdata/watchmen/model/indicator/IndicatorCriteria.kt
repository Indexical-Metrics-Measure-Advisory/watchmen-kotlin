package com.matrdata.watchmen.model.indicator

import com.matrdata.watchmen.model.common.BucketId
import com.matrdata.watchmen.model.common.DataModel
import com.matrdata.watchmen.model.common.FactorOrSubjectDatasetColumnId

sealed interface IndicatorCriteria : DataModel {
	var factorId: FactorOrSubjectDatasetColumnId?
}

/**
 * fill when use predefined bucket
 */
data class IndicatorCriteriaOnBucket(
	override var factorId: FactorOrSubjectDatasetColumnId? = null,
	var bucketId: BucketId? = null,
	var bucketSegmentName: String? = null
) : IndicatorCriteria

enum class IndicatorCriteriaOperator(val code: String) {
	EQUALS("equals"),
	NOT_EQUALS("not-equals"),
	LESS("less"),
	LESS_EQUALS("less-equals"),
	MORE("more"),
	MORE_EQUALS("more-equals")
}

data class IndicatorCriteriaOnExpression(
	override var factorId: FactorOrSubjectDatasetColumnId? = null,
	var operator: IndicatorCriteriaOperator? = IndicatorCriteriaOperator.EQUALS,
	var value: String? = null
) : IndicatorCriteria
