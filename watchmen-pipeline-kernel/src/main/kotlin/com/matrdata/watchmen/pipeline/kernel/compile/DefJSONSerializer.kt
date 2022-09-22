package com.matrdata.watchmen.pipeline.kernel.compile

import com.matrdata.watchmen.model.common.Conditional
import com.matrdata.watchmen.utils.toJSON

typealias DefJSON = String

fun Conditional.toPrerequisiteDefJSON(): DefJSON {
	return mapOf(
		"conditional" to (this.conditional ?: false).toString(),
		"on" to this.on.toJSON()
	).toJSON()
}