package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

data class EChartsGrid(
	var show: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	override var border: EChartsBorderOmitRadius? = null,
	override var position: EChartsPosition? = null
) : EChartsBorderOmitRadiusHolder, EChartsPositionHolder, DataModel

sealed interface EChartsGridHolder {
	var grid: EChartsGrid?
}

sealed interface EChartsGridPositionOnly : EChartsPositionHolder
