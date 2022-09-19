package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel
import java.math.BigDecimal

enum class EChartsAxisSplitLineStyle(val code: String) {
	SOLID("solid"),
	DASHED("dashed"),
	DOTTED("dotted")
}

data class EChartsAxisSplitLine(
	var show: Boolean? = null,
	var color: ChartColor? = null,
	var width: BigDecimal? = null,
	var style: EChartsAxisSplitLineStyle? = null
) : DataModel

sealed interface EChartsAxisSplitLineHolder {
	var splitLine: EChartsAxisSplitLine?
}

sealed interface EChartsAxisMinorSplitLineHolder {
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

data class EChartsXAxisName(
	var text: String? = null,
	var location: EChartsXAxisNameLocation? = null,
	var backgroundColor: ChartColor? = null,
	var gap: BigDecimal? = null,
	var rotate: BigDecimal? = null,
	var padding: BigDecimal? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null,
	override var border: ChartBorder? = null,
	override var font: ChartFont? = null
) : EChartsFontHolder, EChartsBorderHolder, EChartsAlignmentHolder, DataModel

data class EChartsXAxisLabel(
	var show: Boolean? = null,
	var inside: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	var gap: BigDecimal? = null,
	var rotate: BigDecimal? = null,
	var padding: BigDecimal? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null,
	override var border: ChartBorder? = null,
	override var font: ChartFont? = null
) : EChartsFontHolder, EChartsBorderHolder, EChartsAlignmentHolder, DataModel

data class EChartsXAxis(
	var show: Boolean? = null,
	var position: EChartsXAxisPosition? = null,
	var type: EChartsXAxisType? = null,
	var name: EChartsXAxisName? = null,
	var label: EChartsXAxisLabel? = null,
	var autoMin: Boolean? = null,
	var min: BigDecimal? = null,
	var autoMax: Boolean? = null,
	var max: BigDecimal? = null,
	override var splitLine: EChartsAxisSplitLine? = null,
	override var minorSplitLine: EChartsAxisSplitLine? = null
) : EChartsAxisSplitLineHolder, EChartsAxisMinorSplitLineHolder, DataModel

sealed interface EChartsXAxisHolder {
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

data class EChartsYAxisName(
	var text: String? = null,
	var location: EChartsYAxisNameLocation? = null,
	var backgroundColor: ChartColor? = null,
	var gap: BigDecimal? = null,
	var rotate: BigDecimal? = null,
	var padding: BigDecimal? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null,
	override var border: ChartBorder? = null,
	override var font: ChartFont? = null
) : EChartsFontHolder, EChartsBorderHolder, EChartsAlignmentHolder, DataModel

data class EChartsYAxisLabel(
	var show: Boolean? = null,
	var inside: Boolean? = null,
	var backgroundColor: ChartColor? = null,
	var gap: BigDecimal? = null,
	var rotate: BigDecimal? = null,
	var padding: BigDecimal? = null,
	override var horizontalAlign: EChartsHorizontalAlignment? = null,
	override var verticalAlign: EChartsVerticalAlignment? = null,
	override var border: ChartBorder? = null,
	override var font: ChartFont? = null
) : EChartsFontHolder, EChartsBorderHolder, EChartsAlignmentHolder, DataModel

data class EChartsYAxis(
	var show: Boolean? = null,
	var position: EChartsYAxisPosition? = null,
	var type: EChartsYAxisType? = null,
	var name: EChartsYAxisName? = null,
	var label: EChartsYAxisLabel? = null,
	var autoMin: Boolean? = null,
	var min: BigDecimal? = null,
	var autoMax: Boolean? = null,
	var max: BigDecimal? = null,
	override var splitLine: EChartsAxisSplitLine? = null,
	override var minorSplitLine: EChartsAxisSplitLine? = null
) : EChartsAxisSplitLineHolder, EChartsAxisMinorSplitLineHolder, DataModel

sealed interface EChartsYAxisHolder {
	var yaxis: EChartsYAxis?
}