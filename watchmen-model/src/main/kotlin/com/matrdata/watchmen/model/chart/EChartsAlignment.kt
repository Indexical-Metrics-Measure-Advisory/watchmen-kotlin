package com.matrdata.watchmen.model.chart

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

sealed interface EChartsAlignmentHolder {
	var horizontalAlign: EChartsHorizontalAlignment?
	var verticalAlign: EChartsVerticalAlignment?
}
