package com.matrdata.watchmen.model.chart

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

data class ChartFont(
	var family: String? = null,
	var size: String? = null,
	var color: ChartColor? = null,
	var style: ChartFontStyle? = null,
	var weight: ChartFontWeight? = null
)