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
 * adapt this to which given function returned
 */
@OptIn(ExperimentalContracts::class)
inline fun <T, R> T.adapt(block: T.() -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block()
}

/**
 * hand this to given function, and gather returned by it
 */
@OptIn(ExperimentalContracts::class)
inline fun <T, R> T.handTo(block: (T) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block(this)
}

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> T?.throwIfNull(block: T?.() -> String): T {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
		returns() implies (this@throwIfNull != null)
	}

	if (this == null) {
		throw IllegalArgumentException(block())
	} else {
		return this
	}
}