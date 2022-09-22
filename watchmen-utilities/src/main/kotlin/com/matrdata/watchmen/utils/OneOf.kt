package com.matrdata.watchmen.utils

sealed class OneOf(private val kept: Any) {
	/**
	 * get internal kept one
	 */
	fun <X> get(): X {
		@Suppress("UNCHECKED_CAST")
		return kept as X
	}

	fun <X> from(cls: Class<X>, block: X.() -> Unit): OneOf {
		@Suppress("UNCHECKED_CAST")
		cls.isAssignableFrom(kept.javaClass).doIfTrue { (kept as X).block() }
		return this
	}
}

class OneOf2<A, B> private constructor(kept: Any) : OneOf(kept) {
	companion object {
		fun <A, B> ofA(a: A): OneOf2<A, B> {
			return OneOf2(a!!)
		}

		fun <A, B> ofB(b: B): OneOf2<A, B> {
			return OneOf2(b!!)
		}
	}
}
