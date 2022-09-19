package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

enum class TreeLayout(val code: String) {
	ORTHOGONAL("orthogonal"),
	RADIAL("radial")
}

enum class TreeOrient(val code: String) {
	LEFT_RIGHT("LR"),
	RIGHT_LEFT("RL"),
	TOP_BOTTOM("TB"),
	BOTTOM_TOP("BT"),
}

data class TreeChartSettingsSeries(
	var layout: TreeLayout? = null,
	var orient: TreeOrient? = null,
	var roam: Boolean? = null
) : DataModel

data class TreeChartSettings(
	var series: TreeChartSettingsSeries? = null,
	var grid: EChartsGridPositionOnly? = null,
	override var border: ChartBorder? = null,
	override var backgroundColor: ChartColor? = null,
	override var colorSeries: PredefinedChartColorSeries? = PredefinedChartColorSeries.REGULAR,
	override var truncation: ChartTruncation? = null,
	override var title: EChartsTitle? = null
) : EChartsSettings

data class TreeChart(
	override var settings: TreeChartSettings? = null,
) : Chart<TreeChartSettings>(type = ChartType.TREE, settings = settings) {
	@Suppress("UNUSED_PARAMETER")
	override var type: ChartType
		get() = ChartType.TREE
		set(value) {}
}
