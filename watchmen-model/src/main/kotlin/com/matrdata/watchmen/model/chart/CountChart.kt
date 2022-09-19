package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

data class CountChartSettingsText(
	var font: ChartFont? = null,
	var formatUseGrouping: Boolean? = null
) : DataModel

data class CountChartSettings(
	var countText: CountChartSettingsText? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var title: EChartsTitle? = null
) : EChartsSettings

data class CountChart(
	override var settings: CountChartSettings? = null,
) : Chart<CountChartSettings>(type = ChartType.COUNT, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType
		get() = ChartType.COUNT
		set(value) {}
}