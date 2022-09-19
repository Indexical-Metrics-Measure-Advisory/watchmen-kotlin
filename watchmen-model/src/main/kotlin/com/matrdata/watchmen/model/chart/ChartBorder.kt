package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

enum class ChartBorderStyle(val code: String) {
	NONE("none"),
	SOLID("solid"),
	DOTTED("dotted"),
	DASHED("dashed")
}

data class ChartBorder(
	var color: ChartColor? = null,
	var style: ChartBorderStyle? = null,
	var width: String? = null,
	var radius: String? = null
) : DataModel