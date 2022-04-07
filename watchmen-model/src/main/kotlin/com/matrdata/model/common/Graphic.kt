package com.matrdata.model.common

interface GraphicPosition {
	var x: Double
	var y: Double
}


interface GraphicSize {
	var width: Double
	var height: Double
}

class GraphicRect(
	override var x: Double = 0.0,
	override var y: Double = 0.0,
	override var width: Double = 0.0,
	override var height: Double = 0.0
) : GraphicPosition, GraphicSize
