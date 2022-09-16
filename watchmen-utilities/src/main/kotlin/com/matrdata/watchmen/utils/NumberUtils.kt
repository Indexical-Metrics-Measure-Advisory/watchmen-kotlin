package com.matrdata.watchmen.utils

import java.math.BigDecimal
import java.math.BigInteger

object NumberUtils {
	/**
	 * cast given value to decimal, byte/short/int/long/float/double/big integer/big decimal is supported.
	 * otherwise returns null
	 */
	fun toDecimal(value: Number): BigDecimal? {
		return when (value) {
			is Byte -> BigDecimal.valueOf(value.toLong())
			is Short -> BigDecimal.valueOf(value.toLong())
			is Int -> BigDecimal.valueOf(value.toLong())
			is Long -> BigDecimal.valueOf(value.toLong())
			is Float -> BigDecimal.valueOf(value.toDouble())
			is Double -> BigDecimal.valueOf(value.toDouble())
			is BigInteger -> value.toBigDecimal()
			is BigDecimal -> value
			else -> null
		}
	}

	/**
	 * cast given value to decimal, or returns null when cannot cast
	 */
	fun toDecimal(value: Any?): BigDecimal? {
		return when (value) {
			null, "" -> null
			is Number -> toDecimal(value)
			is String -> try {
				return BigDecimal(value)
			} catch (e: Exception) {
				return null
			}

			else -> null
		}
	}
}