package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

data class TreemapChartSettingsSeries(
	var roam: Boolean? = null
) : DataModel

data class TreemapChartSettings(
	var series: TreemapChartSettingsSeries? = null,
	var grid: EChartsGridPositionOnly? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var title: EChartsTitle? = null
) : EChartsSettings

data class TreemapChart(
	override var settings: TreemapChartSettings? = null,
) : Chart<TreemapChartSettings>(type = ChartType.TREEMAP, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType
		get() = ChartType.TREEMAP
		set(value) {}
}
