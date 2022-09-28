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

/**
 * unbox pair and hand first and second as arguments separately to given function, and gather returned by it
 */
@OptIn(ExperimentalContracts::class)
inline fun <F, T, P : Pair<F, T>, R> P.handTo(block: (F, T) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block(this.first, this.second)
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

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> T?.throwIfNull2(block: T?.() -> Throwable): T {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
		returns() implies (this@throwIfNull2 != null)
	}

	if (this == null) {
		throw block()
	} else {
		return this
	}
}

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> T?.useIfNull(block: () -> T?): T? {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}

	if (this == null) {
		return block()
	} else {
		return this
	}
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Collection<T>?.throwIfNullOrEmpty(block: Collection<T>?.() -> String): Collection<T> {
	contract {
		returns() implies (this@throwIfNullOrEmpty != null)
	}

	if (this.isNullOrEmpty()) {
		throw IllegalArgumentException(block())
	} else {
		return this
	}
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Collection<T>?.throwIfNullOrEmpty2(block: Collection<T>?.() -> Throwable): Collection<T> {
	contract {
		returns() implies (this@throwIfNullOrEmpty2 != null)
	}

	if (this.isNullOrEmpty()) {
		throw block()
	} else {
		return this
	}
}
