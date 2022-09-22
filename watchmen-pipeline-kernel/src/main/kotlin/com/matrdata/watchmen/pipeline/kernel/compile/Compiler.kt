package com.matrdata.watchmen.pipeline.kernel.compile

import com.matrdata.watchmen.auth.Principal

/**
 * compiler, which always need a principal to compile things
 */
interface Compiler<X> {
	fun compileBy(principal: Principal): X
}

/**
 * all materials are prepared
 */
interface PreparedCompiler<X> {
	fun compile(): X
}