package com.matrdata.watchmen.model.chart

data class CustomizedChartSettings(
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var script: String? = null,
	override var scriptVarsDefs: String? = null,
	override var scriptVars: EChartsScriptsVars? = null,
	override var title: EChartsTitle? = null
) : EChartsSettings, EchartsScriptHolder

data class CustomizedChart(
	override var settings: CustomizedChartSettings? = null,
) : Chart<CustomizedChartSettings>(type = ChartType.CUSTOMIZED, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType
		get() = ChartType.CUSTOMIZED
		set(value) {}
}
