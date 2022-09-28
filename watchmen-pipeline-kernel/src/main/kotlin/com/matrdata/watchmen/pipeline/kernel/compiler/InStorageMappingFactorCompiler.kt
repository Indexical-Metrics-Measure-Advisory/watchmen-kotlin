package com.matrdata.watchmen.pipeline.kernel.compiler

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.MappingFactor
import com.matrdata.watchmen.pipeline.kernel.compiled.CompiledInStorageMappingFactor

class InStorageMappingFactorCompiler private constructor(private val mapping: MappingFactor) :
	Compiler<CompiledInStorageMappingFactor> {
	companion object {
		fun of(mapping: MappingFactor): InStorageMappingFactorCompiler {
			return InStorageMappingFactorCompiler(mapping)
		}
	}

	override fun compileBy(principal: Principal): CompiledInStorageMappingFactor {
		return CompiledInStorageMappingFactor(
			mapping = this.mapping,
		)
	}
}