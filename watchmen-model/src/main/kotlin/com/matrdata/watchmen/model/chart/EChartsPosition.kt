package com.matrdata.watchmen.model.chart

import com.matrdata.watchmen.model.common.DataModel
import java.math.BigDecimal

data class EChartsPosition(
	var top: BigDecimal? = null,
	var right: BigDecimal? = null,
	var left: BigDecimal? = null,
	var bottom: BigDecimal? = null
) : DataModel

sealed interface EChartsPositionHolder {
	var position: EChartsPosition?
}
