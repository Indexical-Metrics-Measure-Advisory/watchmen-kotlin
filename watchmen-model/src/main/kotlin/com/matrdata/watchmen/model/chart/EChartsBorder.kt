package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

sealed interface EChartsBorderHolder {
	var border: ChartBorder?
}

data class EChartsBorderOmitRadius(
	var color: ChartColor? = null,
	var style: ChartBorderStyle? = null,
	var width: String? = null
) : DataModel


sealed interface EChartsBorderOmitRadiusHolder {
	var border: EChartsBorderOmitRadius?
}