package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.model.common.Conditional
import com.matrdata.watchmen.pipeline.kernel.compiled.DefJSON
import com.matrdata.watchmen.utils.toJSON

fun Conditional.toPrerequisiteDefJSON(): DefJSON {
	return mapOf(
		"conditional" to (this.conditional ?: false).toString(),
		"on" to this.on.toJSON()
	).toJSON()
}