package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

data class ScatterChartSettings(
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var xaxis: EChartsXAxis? = null,
	override var yaxis: EChartsYAxis? = null,
	override var grid: EChartsGrid? = null,
	override var legend: EChartsLegend? = null,
	override var title: EChartsTitle? = null
) : EChartsSettings, EChartsLegendHolder, EChartsGridHolder, EChartsXAxisHolder, EChartsYAxisHolder, DataModel


data class ScatterChart(
	override var settings: ScatterChartSettings? = null,
) : Chart<ScatterChartSettings>(type = ChartType.SCATTER, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType
		get() = ChartType.SCATTER
		set(value) {}
}
