package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

sealed interface ChartSettings : ChartTruncationHolder {
	var border: ChartBorder?
	var backgroundColor: ChartColor?
	var colorSeries: PredefinedChartColorSeries?
}

sealed class Chart<S : ChartSettings>(
	open var type: ChartType?,
	open var settings: S? = null,
) : DataModel
