package com.matrdata.watchmen.model.common

interface GraphicPosition {
	var x: Float
	var y: Float
}

data class StandardGraphicPosition(
	var x: Float = 0f,
	var y: Float = 0f
)


sealed interface GraphicSize {
	var width: Float
	var height: Float
}

data class StandardGraphicSize(
	var width: Float = 0f,
	var height: Float = 0f
)

data class GraphicRect(
	override var x: Float = 0f,
	override var y: Float = 0f,
	override var width: Float = 0f,
	override var height: Float = 0f
) : GraphicPosition, GraphicSize
