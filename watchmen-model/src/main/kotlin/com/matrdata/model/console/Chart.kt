package com.matrdata.model.console

enum class ChartType(val code: String) {
	COUNT("count"),
	BAR("bar"),
	LINE("line"),
	SCATTER("scatter"),
	PIE("pie"),
	DOUGHNUT("doughnut"),
	NIGHTINGALE("nightingale"),
	SUNBURST("sunburst"),
	TREE("tree"),
	TREEMAP("treemap"),
	MAP("map"),
	CUSTOMIZED("customized")
}

typealias ChartColor = String

enum class ChartFontStyle(val code: String) {
	NORMAL("normal"),
	ITALIC("italic")
}

enum class ChartFontWeight(val code: String) {
	W100("100"),
	W200("200"),
	W300("300"),
	W400("400"),
	W500("500"),
	W600("600"),
	W700("700"),
	W800("800"),
	W900("900")
}

class ChartFont(
	var family: String? = null,
	var size: Double? = null,
	var color: ChartColor? = null,
	var style: ChartFontStyle? = null,
	var weight: ChartFontWeight? = null
)

enum class ChartBorderStyle(val code: String) {
	NONE("none"),
	SOLID("solid"),
	DOTTED("dotted"),
	DASHED("dashed")
}

class ChartBorder(
	var color: ChartColor? = null,
	var style: ChartBorderStyle? = null,
	var width: Double? = null,
	var radius: Double? = null,
)

enum class ChartTruncationType(val code: String) {
	NONE("none"),
	TOP("top"),
	BOTTOM("bottom")
}

class ChartTruncation(
	var type: ChartTruncationType? = ChartTruncationType.NONE,
	var count: Int? = 20
)

enum class PredefinedChartColorSeries(val code: String) {
	REGULAR("regular"),
	DARK("dark"),
	LIGHT("light")
}

interface ChartTruncationHolder {
	var truncation: ChartTruncation?
}

interface ChartSettings : ChartTruncationHolder {
	var border: ChartBorder?
	var backgroundColor: ChartColor?
	var colorSeries: PredefinedChartColorSeries?
}

interface Chart<Settings : ChartSettings> {
	var type: ChartType?
	var settings: Settings?
}
