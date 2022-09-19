package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel
import java.math.BigDecimal

enum class EChartsLegendOrient(val code: String) {
	HORIZONTAL("horizontal"),
	VERTICAL("vertical")
}

data class EChartsLegend(
	var show: Boolean? = null,
	var orient: EChartsLegendOrient? = null,
	var backgroundColor: ChartColor? = null,
	var padding: BigDecimal? = null,
	override var border: ChartBorder? = null,
	override var font: ChartFont? = null,
	override var position: EChartsPosition? = null
) : EChartsBorderHolder, EChartsPositionHolder, EChartsFontHolder, DataModel

sealed interface EChartsLegendHolder {
	var legend: EChartsLegend?
}