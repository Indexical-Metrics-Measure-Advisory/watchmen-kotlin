package com.matrdata.watchmen.model.common

import java.math.BigDecimal

interface GraphicPosition : DataModel {
	var x: BigDecimal
	var y: BigDecimal
}

data class StandardGraphicPosition(
	var x: BigDecimal = BigDecimal.ZERO,
	var y: BigDecimal = BigDecimal.ZERO
)


sealed interface GraphicSize : DataModel {
	var width: BigDecimal
	var height: BigDecimal
}

data class StandardGraphicSize(
	var width: BigDecimal = BigDecimal.ZERO,
	var height: BigDecimal = BigDecimal.ZERO
)

data class GraphicRect(
	override var x: BigDecimal = BigDecimal.ZERO,
	override var y: BigDecimal = BigDecimal.ZERO,
	override var width: BigDecimal = BigDecimal.ZERO,
	override var height: BigDecimal = BigDecimal.ZERO
) : GraphicPosition, GraphicSize
