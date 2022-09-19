package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel

enum class EChartsToolboxOrient(val code: String) {
	HORIZONTAL("horizontal"),
	VERTICAL("vertical")
}

data class EChartsToolbox(
	var show: Boolean? = null,
	var orient: EChartsToolboxOrient? = null,
	override var position: EChartsPosition? = null
) : EChartsPositionHolder, DataModel

sealed interface EChartsToolboxHolder {
	var toolbox: EChartsToolbox?
}