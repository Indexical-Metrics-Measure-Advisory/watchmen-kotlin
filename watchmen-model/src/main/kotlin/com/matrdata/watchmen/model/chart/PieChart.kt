package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel
import java.math.BigDecimal

enum class PieRoseType(val code: String) {
	NONE("none"),
	RADIUS("radius"),
	AREA("area")
}

enum class PieLabelPosition(val code: String) {
	INSIDE("inside"),
	OUTSIDE("outside"),
	CENTER("center")
}

enum class PieLabelAlignTo(val code: String) {
	NONE("none"),
	LABEL_LINE("labelLine"),
	EDGE("edge")
}

data class PieChartSettingsLabel(
	var show: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	var position: PieLabelPosition? = null,
	var alignTo: PieLabelAlignTo? = null,
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

sealed interface PieStyleChartSettingsSeries : EChartsBorderHolder, DataModel {
	var centerX: BigDecimal?
	var centerY: BigDecimal?
	var insideRadius: BigDecimal?
	var outsideRadius: BigDecimal?
}

data class PieChartSettingsSeries(
	override var centerX: BigDecimal? = null,
	override var centerY: BigDecimal? = null,
	override var insideRadius: BigDecimal? = null,
	override var outsideRadius: BigDecimal? = null,
	var roseType: PieRoseType? = null,
	var showPercentage: Boolean? = null,
	override var border: ChartBorder? = null
) : PieStyleChartSettingsSeries

sealed interface PieStyleChartSettings<S : PieStyleChartSettingsSeries> : EChartsSettings, EChartsLegendHolder,
	DataModel {
	var series: S?
	var grid: EChartsGridPositionOnly?
	var label: PieChartSettingsLabel?
	var decal: Boolean?
}

data class PieChartSettings(
	override var series: PieChartSettingsSeries? = null,
	override var grid: EChartsGridPositionOnly? = null,
	override var label: PieChartSettingsLabel? = null,
	override var decal: Boolean? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var legend: EChartsLegend? = null,
	override var title: EChartsTitle? = null
) : PieStyleChartSettings<PieChartSettingsSeries>

data class PieChart(
	override var settings: PieChartSettings? = null,
) : Chart<PieChartSettings>(type = ChartType.PIE, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType?
		get() = ChartType.PIE
		set(value) {}
}

data class DoughnutChartSettings(
	override var series: PieChartSettingsSeries? = null,
	override var grid: EChartsGridPositionOnly? = null,
	override var label: PieChartSettingsLabel? = null,
	override var decal: Boolean? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var legend: EChartsLegend? = null,
	override var title: EChartsTitle? = null
) : PieStyleChartSettings<PieChartSettingsSeries>

data class DoughnutChart(
	override var settings: DoughnutChartSettings? = null,
) : Chart<DoughnutChartSettings>(type = ChartType.DOUGHNUT, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType?
		get() = ChartType.DOUGHNUT
		set(value) {}
}

data class NightingaleChartSettings(
	override var series: PieChartSettingsSeries? = null,
	override var grid: EChartsGridPositionOnly? = null,
	override var label: PieChartSettingsLabel? = null,
	override var decal: Boolean? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var legend: EChartsLegend? = null,
	override var title: EChartsTitle? = null
) : PieStyleChartSettings<PieChartSettingsSeries>

data class NightingaleChart(
	override var settings: NightingaleChartSettings? = null,
) : Chart<NightingaleChartSettings>(type = ChartType.NIGHTINGALE, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType?
		get() = ChartType.NIGHTINGALE
		set(value) {}
}

data class SunburstChartSettingsSeries(
	override var centerX: BigDecimal? = null,
	override var centerY: BigDecimal? = null,
	override var insideRadius: BigDecimal? = null,
	override var outsideRadius: BigDecimal? = null,
	override var border: ChartBorder? = null,
) : PieStyleChartSettingsSeries

data class SunburstChartSettings(
	override var series: SunburstChartSettingsSeries? = null,
	override var grid: EChartsGridPositionOnly? = null,
	override var label: PieChartSettingsLabel? = null,
	override var decal: Boolean? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var legend: EChartsLegend? = null,
	override var title: EChartsTitle? = null
) : PieStyleChartSettings<SunburstChartSettingsSeries>

data class SunburstChart(
	override var settings: SunburstChartSettings? = null,
) : Chart<SunburstChartSettings>(type = ChartType.SUNBURST, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType?
		get() = ChartType.SUNBURST
		set(value) {}
}
