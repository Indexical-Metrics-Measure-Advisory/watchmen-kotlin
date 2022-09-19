package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel
import java.math.BigDecimal

data class EChartsTitleText(
	var text: String? = null,
	override var font: ChartFont? = null
) : EChartsFontHolder, DataModel

data class EChartsTitle(
	var text: EChartsTitleText? = null,
	var subtext: EChartsTitleText? = null,
	var backgroundColor: ChartColor? = null,
	var padding: BigDecimal? = null,
	var itemGap: BigDecimal? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null,
	override var border: ChartBorder? = null,
	override var position: EChartsPosition? = null
) : EChartsBorderHolder, EChartsPositionHolder, EChartsAlignmentHolder, DataModel

sealed interface EChartsTitleHolder {
	var title: EChartsTitle?
}
