package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.model.admin.AggregateArithmetic
import com.matrdata.watchmen.model.admin.MappingFactor

class CompiledInStorageMappingFactor(val mapping: MappingFactor) {
	val arithmetic: AggregateArithmetic get() = this.mapping.arithmetic ?: AggregateArithmetic.NONE
}