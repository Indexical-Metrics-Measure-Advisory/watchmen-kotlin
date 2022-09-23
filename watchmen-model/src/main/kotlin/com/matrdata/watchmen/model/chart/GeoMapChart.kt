package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

enum class GeoMapChartRegion(val code: String) {
	CHINA_L1("china-l1"),
	CYPRUS_L1("cyprus-l1"),
	JAPAN_L1("japan-l1"),
	SINGAPORE_L1("singapore-l1"),
	USA_L1("usa-l1")
}

data class GeoMapChartSettingsSeries(
	var region: GeoMapChartRegion? = null
) : DataModel

data class GeoMapChartSettings(
	var series: GeoMapChartSettingsSeries? = null,
	var grid: EChartsGridPositionOnly? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var title: EChartsTitle? = null
) : EChartsSettings

data class GeoMapChart(
	override var settings: GeoMapChartSettings? = null,
) : Chart<GeoMapChartSettings>(type = ChartType.MAP, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType?
		get() = ChartType.MAP
		set(value) {}
}
