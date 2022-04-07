package com.matrdata.model.console

interface EChartsFontHolder {
	var font: ChartFont?
}

class EChartsTitleText(
	var text: String? = null,
	override var font: ChartFont? = null
) : EChartsFontHolder

interface EChartsBorderHolder {
	var border: ChartBorder?
}

class EChartsPosition(
	var top: Double? = null,
	var right: Double? = null,
	var left: Double? = null,
	var bottom: Double? = null
)

interface EChartsPositionHolder {
	var position: EChartsPosition?
}

enum class EChartsHorizontalAlignment(val code: String) {
	AUTO("auto"),
	LEFT("left"),
	RIGHT("right"),
	CENTER("center")
}

enum class EChartsVerticalAlignment(val code: String) {
	AUTO("auto"),
	TOP("top"),
	BOTTOM("bottom"),
	MIDDLE("middle")
}

interface EChartsAlignmentHolder {
	var horizontalAlign: EChartsHorizontalAlignment?
	var verticalAlign: EChartsVerticalAlignment?
}

class EChartsTitle(
	var text: EChartsTitleText? = null,
	var subtext: EChartsTitleText? = null,
	var backgroundColor: ChartColor? = null,
	var padding: Double? = null,
	var itemGap: Double? = null,
	override var border: ChartBorder? = null,
	override var position: EChartsPosition? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null
) : EChartsBorderHolder, EChartsPositionHolder, EChartsAlignmentHolder

interface EChartsTitleHolder {
	var title: EChartsTitle?
}

enum class EChartsLegendOrient(val code: String) {
	HORIZONTAL("horizontal"),
	VERTICAL("vertical")
}

class EChartsLegend(
	var show: Boolean? = null,
	var orient: EChartsLegendOrient? = null,
	var backgroundColor: ChartColor? = null,
	var padding: Double? = null,
	override var font: ChartFont? = null,
	override var border: ChartBorder? = null,
	override var position: EChartsPosition? = null
) : EChartsBorderHolder, EChartsPositionHolder, EChartsFontHolder

interface EChartsLegendHolder {
	var legend: EChartsLegend?
}

class EChartsBorderOmitRadius(
	var color: ChartColor? = null,
	var style: ChartBorderStyle? = null,
	var width: Double? = null
)

interface EChartsBorderHolderNoRadius {
	var border: EChartsBorderOmitRadius?
}

class EChartsGrid(
	var show: Boolean? = null,
	var containLabel: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	override var position: EChartsPosition? = null,
	override var border: EChartsBorderOmitRadius? = null
) : EChartsBorderHolderNoRadius, EChartsPositionHolder

interface EChartsGridHolder {
	var grid: EChartsGrid?
}

interface EChartsGridPositionOnly {
	var grid: EChartsPositionHolder?
}

enum class EChartsAxisSplitLineStyle(val code: String) {
	SOLID("solid"),
	DASHED("dashed"),
	DOTTED("dotted")
}

class EChartsAxisSplitLine(
	var show: Boolean? = null,
	var color: ChartColor? = null,
	var width: Double? = null,
	var style: EChartsAxisSplitLineStyle? = null,
)

interface EChartsAxisSplitLineHolder {
	var splitLine: EChartsAxisSplitLine?
}

interface EChartsAxisMinorSplitLineHolder {
	var minorSplitLine: EChartsAxisSplitLine?
}

enum class EChartsXAxisPosition(val code: String) {
	TOP("top"),
	BOTTOM("bottom")
}

enum class EChartsXAxisType(val code: String) {
	VALUE("value"),
	CATEGORY("category"),
	TIME("time")
}

enum class EChartsXAxisNameLocation(val code: String) {
	START("start"),
	CENTER("center"),
	END("end")
}

class EChartsXAxisName(
	var text: String? = null,
	var location: EChartsXAxisNameLocation? = null,
	var backgroundColor: ChartColor? = null,
	var gap: Double? = null,
	var rotate: Double? = null,
	var padding: Double? = null,
	override var font: ChartFont? = null,
	override var border: ChartBorder? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null
) : EChartsFontHolder, EChartsBorderHolder, EChartsAlignmentHolder

class EChartsXAxisLabel(
	var show: Boolean? = null,
	var inside: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	var gap: Double? = null,
	var rotate: Double? = null,
	var padding: Double? = null,
	override var font: ChartFont? = null,
	override var border: ChartBorder?,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null
) : EChartsFontHolder, EChartsBorderHolder, EChartsAlignmentHolder

class EChartsXAxis(
	var show: Boolean? = null,
	var position: EChartsXAxisPosition? = null,
	var type: EChartsXAxisType? = null,
	var name: EChartsXAxisName? = null,
	var label: EChartsXAxisLabel? = null,
	var autoMin: Boolean? = null,
	var min: Double? = null,
	var autoMax: Boolean? = null,
	var max: Double? = null,
	override var splitLine: EChartsAxisSplitLine? = null,
	override var minorSplitLine: EChartsAxisSplitLine? = null
) : EChartsAxisSplitLineHolder, EChartsAxisMinorSplitLineHolder

interface EChartsXAxisHolder {
	var xaxis: EChartsXAxis?
}

enum class EChartsYAxisPosition(val code: String) {
	LEFT("left"),
	RIGHT("right")
}

enum class EChartsYAxisType(val code: String) {
	VALUE("value"),
	CATEGORY("category"),
	TIME("time")
}

enum class EChartsYAxisNameLocation(val code: String) {
	START("start"),
	MIDDLE("middle"),
	END("end")
}

class EChartsYAxisName(
	var text: String? = null,
	var location: EChartsYAxisNameLocation? = null,
	var backgroundColor: ChartColor? = null,
	var gap: Double? = null,
	var rotate: Double? = null,
	var padding: Double? = null,
	override var font: ChartFont? = null,
	override var border: ChartBorder? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null
) : EChartsFontHolder, EChartsBorderHolder, EChartsAlignmentHolder

class EChartsYAxisLabel(
	var show: Boolean? = null,
	var inside: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	var gap: Double? = null,
	var rotate: Double? = null,
	var padding: Double? = null,
	override var font: ChartFont? = null,
	override var border: ChartBorder? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null
) : EChartsFontHolder, EChartsBorderHolder, EChartsAlignmentHolder

class EChartsYAxis(
	var show: Boolean? = null,
	var position: EChartsYAxisPosition? = null,
	var type: EChartsYAxisType? = null,
	var name: EChartsYAxisName? = null,
	var label: EChartsYAxisLabel? = null,
	var autoMin: Boolean? = null,
	var min: Double? = null,
	var autoMax: Boolean? = null,
	var max: Double? = null,
	override var splitLine: EChartsAxisSplitLine? = null,
	override var minorSplitLine: EChartsAxisSplitLine? = null
) : EChartsAxisSplitLineHolder, EChartsAxisMinorSplitLineHolder

interface EChartsYAxisHolder {
	var yaxis: EChartsYAxis?
}

interface EChartsSettings : ChartSettings, EChartsTitleHolder

class CountChartSettingsText(
	font: ChartFont? = null,
	formatUseGrouping: Boolean? = null
)

class CountChartSettings(
	countText: CountChartSettingsText? = null,
	override var truncation: ChartTruncation?,
	override var border: ChartBorder?,
	override var backgroundColor: ChartColor?,
	override var colorSeries: PredefinedChartColorSeries?,
	override var title: EChartsTitle?
) : EChartsSettings

class CountChart(
	override var type: ChartType? = ChartType.COUNT,
	override var settings: CountChartSettings? = null
) : Chart<CountChartSettings>

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


class BarChartSettingsLabel(
	var show: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	var position: BarLabelPosition? = null,
	var rotate: Double? = null,
	var gap: Double? = null,
	var padding: Double? = null,
	var formatUseGrouping: Boolean? = null,
	var formatUsePercentage: Boolean? = null,
	var valueAsPercentage: Boolean? = null,
	var fractionDigits: Int? = null,
	override var font: ChartFont? = null,
	override var border: ChartBorder? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null
) : EChartsBorderHolder, EChartsFontHolder, EChartsAlignmentHolder


open class BarChartSettingsSeries(
	open var transformAxis: Boolean? = null
)

class BarChartSettings(
	var series: BarChartSettingsSeries? = null,
	var label: BarChartSettingsLabel? = null,
	var decal: Boolean? = null,
	override var truncation: ChartTruncation?,
	override var border: ChartBorder?,
	override var backgroundColor: ChartColor?,
	override var colorSeries: PredefinedChartColorSeries?,
	override var title: EChartsTitle?,
	override var legend: EChartsLegend?,
	override var grid: EChartsGrid?,
	override var xaxis: EChartsXAxis?,
	override var yaxis: EChartsYAxis?,
) : EChartsSettings, EChartsLegendHolder, EChartsGridHolder, EChartsXAxisHolder, EChartsYAxisHolder

class BarChart(
	override var type: ChartType? = ChartType.BAR,
	override var settings: BarChartSettings? = null
) : Chart<BarChartSettings>

class LineChartSettingsSeries(
	var smooth: Boolean? = null,
	override var transformAxis: Boolean? = null
) : BarChartSettingsSeries(transformAxis)

class LineChartSettings(
	var series: LineChartSettingsSeries? = null,
	var label: BarChartSettingsLabel? = null,
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var legend: EChartsLegend? = null,
	override var grid: EChartsGrid? = null,
	override var xaxis: EChartsXAxis? = null,
	override var yaxis: EChartsYAxis? = null,
) : EChartsSettings, EChartsLegendHolder, EChartsGridHolder, EChartsXAxisHolder, EChartsYAxisHolder

class LineChart(
	override var type: ChartType? = ChartType.LINE,
	override var settings: LineChartSettings? = null
) : Chart<LineChartSettings>

class ScatterChartSettings(
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var legend: EChartsLegend? = null,
	override var grid: EChartsGrid? = null,
	override var xaxis: EChartsXAxis? = null,
	override var yaxis: EChartsYAxis? = null
) : EChartsSettings, EChartsLegendHolder, EChartsGridHolder, EChartsXAxisHolder, EChartsYAxisHolder

class ScatterChart(
	override var type: ChartType? = ChartType.SCATTER,
	override var settings: ScatterChartSettings? = null
) : Chart<ScatterChartSettings>

enum class PieRoseType(val code: String) {
	NONE("none"),
	RADIUS("radius"),
	AREA("area")
}

class PieChartSettingsSeries(
	var centerX: Double? = null,
	var centerY: Double? = null,
	var insideRadius: Double? = null,
	var outsideRadius: Double? = null,
	var roseType: PieRoseType? = null,
	var showPercentage: Boolean? = null,
	override var border: ChartBorder? = null
) : EChartsBorderHolder

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

class PieChartSettingsLabel(
	show: Boolean? = null,
	backgroundColor: ChartColor? = null,
	position: PieLabelPosition? = null,
	alignTo: PieLabelAlignTo? = null,
	rotate: Double? = null,
	gap: Double? = null,
	padding: Double? = null,
	formatUseGrouping: Boolean? = null,
	formatUsePercentage: Boolean? = null,
	valueAsPercentage: Boolean? = null,
	fractionDigits: Int? = null,
	override var font: ChartFont? = null,
	override var border: ChartBorder? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null
) : EChartsBorderHolder, EChartsFontHolder, EChartsAlignmentHolder

open class PieChartSettings(
	open var series: PieChartSettingsSeries? = null,
	open var label: PieChartSettingsLabel? = null,
	open var decal: Boolean? = null,
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var legend: EChartsLegend? = null,
	override var grid: EChartsPositionHolder? = null
) : EChartsSettings, EChartsGridPositionOnly, EChartsLegendHolder

class PieChart(
	override var type: ChartType? = ChartType.PIE,
	override var settings: PieChartSettings? = null
) : Chart<PieChartSettings>

class DoughnutChartSettings(
	override var series: PieChartSettingsSeries? = null,
	override var label: PieChartSettingsLabel? = null,
	override var decal: Boolean? = null,
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var legend: EChartsLegend? = null,
	override var grid: EChartsPositionHolder? = null
) : PieChartSettings()

class DoughnutChart(
	override var type: ChartType? = ChartType.DOUGHNUT,
	override var settings: DoughnutChartSettings? = null
) : Chart<DoughnutChartSettings>

class NightingaleChartSettings(
	override var series: PieChartSettingsSeries? = null,
	override var label: PieChartSettingsLabel? = null,
	override var decal: Boolean? = null,
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var legend: EChartsLegend? = null,
	override var grid: EChartsPositionHolder? = null
) : PieChartSettings()

class NightingaleChart(
	override var type: ChartType? = ChartType.NIGHTINGALE,
	override var settings: NightingaleChartSettings? = null
) : Chart<NightingaleChartSettings>

class SunburstChartSettingsSeries(
	var centerX: Double? = null,
	var centerY: Double? = null,
	var insideRadius: Double? = null,
	var outsideRadius: Double? = null,
	override var border: ChartBorder? = null
) : EChartsBorderHolder

class SunburstChartSettings(
	var series: SunburstChartSettingsSeries? = null,
	var label: PieChartSettingsLabel? = null,
	var decal: Boolean? = null,
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var legend: EChartsLegend? = null,
	override var grid: EChartsPositionHolder? = null
) : EChartsSettings, EChartsGridPositionOnly, EChartsLegendHolder

class SunburstChart(
	override var type: ChartType? = ChartType.SUNBURST,
	override var settings: SunburstChartSettings? = null
) : Chart<SunburstChartSettings>

enum class TreeLayout(val code: String) {
	ORTHOGONAL("orthogonal"),
	RADIAL("radial")
}

enum class TreeOrient(val code: String) {
	LEFT_RIGHT("LR"),
	RIGHT_LEFT("RL"),
	TOP_BOTTOM("TB"),
	BOTTOM_TOP("BT")
}

class TreeChartSettingsSeries(
	var layout: TreeLayout? = null,
	var orient: TreeOrient? = null,
	var roam: Boolean? = null
)

class TreeChartSettings(
	var series: TreeChartSettingsSeries? = null,
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var grid: EChartsPositionHolder? = null
) : EChartsSettings, EChartsGridPositionOnly

class TreeChart(
	override var type: ChartType? = ChartType.TREE,
	override var settings: TreeChartSettings? = null
) : Chart<TreeChartSettings>

class TreemapChartSettingsSeries(
	var roam: Boolean? = null
)

class TreemapChartSettings(
	var series: TreemapChartSettingsSeries? = null,
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var grid: EChartsPositionHolder? = null
) : EChartsSettings, EChartsGridPositionOnly

class TreemapChart(
	override var type: ChartType? = ChartType.TREEMAP,
	override var settings: TreemapChartSettings? = null
) : Chart<TreemapChartSettings>

enum class MapChartRegion(val code: String) {
	CHINA_L1("china-l1"),
	CYPRUS_L1("cyprus-l1"),
	JAPAN_L1("japan-l1"),
	SINGAPORE_L1("singapore-l1"),
	USA_L1("usa-l1")
}

class MapChartSettingsSeries(
	var region: MapChartRegion? = null
)

class MapChartSettings(
	var series: MapChartSettingsSeries? = null,
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var grid: EChartsPositionHolder? = null
) : EChartsSettings, EChartsGridPositionOnly

class MapChart(
	override var type: ChartType? = ChartType.MAP,
	override var settings: MapChartSettings? = null
) : Chart<MapChartSettings>

typealias EchartsScriptsVars = Map<String, String>

interface EchartsScriptHolder {
	var script: String?
	var scriptVarsDefs: String?
	var scriptVars: EchartsScriptsVars?
}

class CustomizedChartSettings(
	override var truncation: ChartTruncation? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = null,
	override var title: EChartsTitle? = null,
	override var script: String? = null,
	override var scriptVarsDefs: String? = null,
	override var scriptVars: EchartsScriptsVars? = null
) : EChartsSettings, EchartsScriptHolder

class CustomizedChart(
	override var type: ChartType? = ChartType.CUSTOMIZED,
	override var settings: CustomizedChartSettings? = null
) : Chart<CustomizedChartSettings>