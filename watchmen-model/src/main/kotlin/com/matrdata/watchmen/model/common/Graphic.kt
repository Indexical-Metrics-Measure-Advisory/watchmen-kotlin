package com.matrdata.watchmen.model.common

import java.math.BigDecimal

interface GraphicPosition : DataModel {
	var x: BigDecimal?
	var y: BigDecimal?
}

data class StandardGraphicPosition(
	override var x: BigDecimal? = BigDecimal.ZERO,
	override var y: BigDecimal? = BigDecimal.ZERO
) : GraphicPosition

sealed interface GraphicSize : DataModel {
	var width: BigDecimal?
	var height: BigDecimal?
}

data class StandardGraphicSize(
	override var width: BigDecimal? = BigDecimal.ZERO,
	override var height: BigDecimal? = BigDecimal.ZERO
) : GraphicSize

data class GraphicRect(
	override var x: BigDecimal? = BigDecimal.ZERO,
	override var y: BigDecimal? = BigDecimal.ZERO,
	override var width: BigDecimal? = BigDecimal.ZERO,
	override var height: BigDecimal? = BigDecimal.ZERO
) : GraphicPosition, GraphicSize
