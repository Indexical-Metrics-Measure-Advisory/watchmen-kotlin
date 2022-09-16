package com.matrdata.watchmen.utils

import com.matrdata.watchmen.utils.DateTimeUtils.isAfter
import com.matrdata.watchmen.utils.DateTimeUtils.isBefore
import com.matrdata.watchmen.utils.DateTimeUtils.toDateTime
import com.matrdata.watchmen.utils.DateTimeUtils.toTime
import com.matrdata.watchmen.utils.NumberUtils.toDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

object ValueUtils {
	/**
	 * returns true when value is null, or empty string, or empty list/map
	 */
	fun isEmpty(value: Any?): Boolean {
		return when (value) {
			null -> true
			is String -> value.isEmpty()
			is List<*> -> value.isEmpty()
			is Map<*, *> -> value.isEmpty()
			else -> false
		}
	}

	/**
	 * returns true when value is not null, and not an empty string, and not an empty list/map
	 */
	fun isNotEmpty(value: Any?): Boolean {
		return !isEmpty(value)
	}

	/**
	 * returns true when value is null or blank string.
	 */
	fun isBlank(value: Any?): Boolean {
		return when (value) {
			null -> true
			is String -> value.isBlank()
			else -> false
		}
	}

	/**
	 * returns true when value is not null, and not a blank string.
	 */
	fun isNotBlank(value: Any?): Boolean {
		return !isBlank(value)
	}

	/**
	 * compare given value with other value only when other one is Byte/Short/Int/Long/Float/Double/BigInteger/BigDecimal/String.
	 * returns true when numeric value is equaled, otherwise returns false.
	 */
	private fun equalsTo(value: Number, other: Any?): Boolean {
		return when (other) {
			// compare wit null, always returns false
			null -> false
			// compare with string
			is String -> value.toString() == other
			// compare with number
			is Number -> toDecimal(value) == toDecimal(other)
			// otherwise always returns false
			else -> false
		}
	}

	/**
	 * compare given value with other value only when
	 * 1. other is number. 1 means true, 0 means false;
	 * 2. other is string. 1/t/true/y/yes means true, 0/f/false/n/no means false;
	 * 3. otherwise false.
	 */
	private fun equalsTo(value: Boolean, other: Any?): Boolean {
		return when (other) {
			null -> false
			is Number -> when (value) {
				true -> other.toString() == "1"
				false -> other.toString() == "0"
			}

			is String -> when (value) {
				true -> arrayOf("1", "t", "true", "y", "yes").any { it.equals(other, true) }
				false -> arrayOf("0", "f", "false", "n", "no").any { it.equals(other, true) }
			}

			else -> false
		}
	}

	/**
	 * microsecond is ignored
	 */
	private fun equalsTo(value: LocalTime, other: Any?, formats: List<String>): Boolean {
		return when (other) {
			null -> false
			is LocalTime -> value.truncatedTo(ChronoUnit.SECONDS) == other.truncatedTo(ChronoUnit.SECONDS)
			// try to parse string to LocalTime
			is String -> toTime(other, formats)?.let {
				value.truncatedTo(ChronoUnit.SECONDS) == it.truncatedTo(ChronoUnit.SECONDS)
			} ?: false

			else -> false
		}
	}

	private fun equalsTo(value: LocalDate, other: Any?, formats: List<String>): Boolean {
		return when (other) {
			null -> false
			is LocalDate -> value == other
			is LocalDateTime -> value == other.toLocalDate()
			// try to parse string to LocalDateTime
			is String -> toDateTime(other, formats)?.let { value == it.toLocalDate() } ?: false

			else -> false
		}
	}

	/**
	 * hour, minute, second, microsecond are ignored
	 */
	private fun equalsTo(value: LocalDateTime, other: Any?, formats: List<String>): Boolean {
		return equalsTo(value.toLocalDate(), other, formats)
	}

	fun equalsTo(value: Any?, other: Any?, timeFormats: List<String>, dateFormats: List<String>): Boolean {
		return when {
			// one is empty
			value == null || value == "" -> other == null || other == ""
			other == null || other == "" -> value == ""

			// one is boolean
			value is Boolean -> equalsTo(value, other)
			other is Boolean -> equalsTo(other, value)

			// one is number
			value is Number -> equalsTo(value, other)
			other is Number -> equalsTo(other, value)

			// one is date/time
			value is LocalTime -> equalsTo(value, other, timeFormats)
			other is LocalTime -> equalsTo(other, value, timeFormats)
			value is LocalDate -> equalsTo(value, other, dateFormats)
			other is LocalDate -> equalsTo(other, value, dateFormats)
			value is LocalDateTime -> equalsTo(value, other, dateFormats)
			other is LocalDateTime -> equalsTo(other, value, dateFormats)

			// string
			value is String || other is String -> value == other

			else -> false
		}
	}

	/**
	 * not equals
	 */
	fun notEqualsTo(value: Any?, other: Any?, timeFormats: List<String>, dateFormats: List<String>): Boolean {
		return !equalsTo(value, other, timeFormats, dateFormats)
	}

	/**
	 * compare the given value and other, returns null when cannot compare
	 */
	fun isLessThan(value: Number, other: Any?, equationAllowed: Boolean): Boolean? {
		return toDecimal(other)
			?.let { Pair(toDecimal(value), it) }
			?.let { values ->
				return values.first?.let { first ->
					if (equationAllowed) {
						return first <= values.second
					} else {
						return first < values.second
					}
				}
			}
	}

	/**
	 * compare the given value and other, returns null when cannot compare
	 */
	fun isGreaterThan(value: Number, other: Any?, equationAllowed: Boolean): Boolean? {
		return toDecimal(other)
			?.let { Pair(toDecimal(value), it) }
			?.let { values ->
				return values.first?.let { first ->
					if (equationAllowed) {
						return first >= values.second
					} else {
						return first > values.second
					}
				}
			}
	}

	/**
	 * compare the given value and other, returns null when cannot compare.
	 * only string/local time can be compared.
	 */
	fun isLessThan(value: LocalTime, other: Any?, formats: List<String>, equationAllowed: Boolean): Boolean? {
		return when (other) {
			null -> null
			is LocalTime -> isBefore(value, other, equationAllowed)
			is String -> toTime(other, formats)
				?.let { Pair(value, it) }
				?.let { values -> isBefore(values.first, values.second, equationAllowed) }

			else -> null
		}
	}

	/**
	 * compare the given value and other, returns null when cannot compare.
	 * only string/local time can be compared.
	 */
	fun isGreaterThan(value: LocalTime, other: Any?, formats: List<String>, equationAllowed: Boolean): Boolean? {
		return when (other) {
			null -> null
			is LocalTime -> isAfter(value, other, equationAllowed)
			is String -> toTime(other, formats)
				?.let { Pair(value, it) }
				?.let { values -> isAfter(values.first, values.second, equationAllowed) }

			else -> null
		}
	}

	/**
	 * compare the given value and other, returns null when cannot compare.
	 * only string/local date can be compared.
	 */
	fun isLessThan(value: LocalDate, other: Any?, formats: List<String>, equationAllowed: Boolean): Boolean? {
		return when (other) {
			null -> null
			is LocalDate -> isBefore(value, other, equationAllowed)
			is String -> toDateTime(other, formats)
				?.let { Pair(value, it.toLocalDate()) }
				?.let { values -> isBefore(values.first, values.second, equationAllowed) }

			else -> null
		}
	}

	/**
	 * compare the given value and other, returns null when cannot compare.
	 * only string/local date can be compared.
	 */
	fun isGreaterThan(value: LocalDate, other: Any?, formats: List<String>, equationAllowed: Boolean): Boolean? {
		return when (other) {
			null -> null
			is LocalDate -> isAfter(value, other, equationAllowed)
			is String -> toDateTime(other, formats)
				?.let { Pair(value, it.toLocalDate()) }
				?.let { values -> isAfter(values.first, values.second, equationAllowed) }

			else -> null
		}
	}

	/**
	 * compare the given value and other, returns null when cannot compare.
	 * only string/local date can be compared.
	 */
	fun isLessThan(value: LocalDateTime, other: Any?, formats: List<String>, equationAllowed: Boolean): Boolean? {
		return isLessThan(value.toLocalDate(), other, formats, equationAllowed)
	}

	/**
	 * compare the given value and other, returns null when cannot compare.
	 * only string/local date can be compared.
	 */
	fun isGreaterThan(value: LocalDateTime, other: Any?, formats: List<String>, equationAllowed: Boolean): Boolean? {
		return isGreaterThan(value.toLocalDate(), other, formats, equationAllowed)
	}
}


