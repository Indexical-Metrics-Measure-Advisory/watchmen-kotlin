package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

enum class ChartTruncationType(val code: String) {
	NONE("none"),
	TOP("top"),
	BOTTOM("bottom")
}

data class ChartTruncation(
	var type: ChartTruncationType? = ChartTruncationType.NONE,
	var count: Int? = 20
) : DataModel

sealed interface ChartTruncationHolder {
	var truncation: ChartTruncation?
}