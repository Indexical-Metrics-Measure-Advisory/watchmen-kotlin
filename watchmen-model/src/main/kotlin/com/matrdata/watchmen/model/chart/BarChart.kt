package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel
import java.math.BigDecimal

enum class BarLabelPosition(val code: String) {
	TOP("top"),
	LEFT("left"),
	RIGHT("right"),
	BOTTOM("bottom"),
	INSIDE("inside"),
	INSIDE_LEFT("insideLeft"),
	INSIDE_RIGHT("insideRight"),
	INSIDE_TOP("insideTop"),
	INSIDE_BOTTOM("insideBottom"),
	INSIDE_TOP_LEFT("insideTopLeft"),
	INSIDE_BOTTOM_LEFT("insideBottomLeft"),
	INSIDE_TOP_RIGHT("insideTopRight"),
	INSIDE_BOTTOM_RIGHT("insideBottomRight")
}

data class BarChartSettingsLabel(
	var show: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	var position: BarLabelPosition? = null,
	var rotate: BigDecimal? = null,
	var gap: BigDecimal? = null,
	var padding: BigDecimal? = null,
	var formatUseGrouping: Boolean? = null,
	var formatUsePercentage: Boolean? = null,
	var valueAsPercentage: Boolean? = null,
	var fractionDigits: Int? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null,
	override var border: ChartBorder? = null,
	override var font: ChartFont? = null
) : EChartsBorderHolder, EChartsFontHolder, EChartsAlignmentHolder, DataModel

data class BarChartSettingsSeries(
	var transformAxis: Boolean? = null
) : DataModel

data class BarChartSettings(
	var series: BarChartSettingsSeries? = null,
	var label: BarChartSettingsLabel? = null,
	var decal: Boolean? = null,
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

data class BarChart(
	override var settings: BarChartSettings? = null,
) : Chart<BarChartSettings>(type = ChartType.BAR, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType
		get() = ChartType.BAR
		set(value) {}
}
