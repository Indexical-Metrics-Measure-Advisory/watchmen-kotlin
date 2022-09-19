package com.matrdata.watchmen.model.chart

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