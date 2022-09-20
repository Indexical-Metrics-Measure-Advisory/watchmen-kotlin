package com.matrdata.watchmen.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * assert this by given function, returns which given function returned.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> T.assert(block: T.() -> Boolean): Boolean {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block()
}

/**
 * transform this to result which given function returned
 */
@OptIn(ExperimentalContracts::class)
inline fun <T, R> T.to(block: T.() -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block()
}