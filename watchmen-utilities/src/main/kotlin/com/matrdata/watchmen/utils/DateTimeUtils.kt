package com.matrdata.watchmen.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap

object DateTimeUtils {
	private val formatters: MutableMap<String, DateTimeFormatter> = ConcurrentHashMap<String, DateTimeFormatter>()

	private fun getFormatter(format: String): DateTimeFormatter {
		val formatter = formatters[format]
		return formatter ?: DateTimeFormatter.ofPattern(format).also { formatters[format] = it }
	}

	private fun toDate(value: String, format: String): LocalDate? {
		return try {
			LocalDate.parse(value, getFormatter(format))
		} catch (e: Exception) {
			null
		}
	}

	fun toDate(value: String?, formats: List<String>): LocalDate? {
		return value?.let {
			formats.stream()
				.map { format -> toDate(it, format) }
				.filter { it != null }
				.findFirst()
				.orElse(null)
		}
	}

	private fun toDateTime(value: String, format: String): LocalDateTime? {
		return try {
			LocalDateTime.parse(value, getFormatter(format))
		} catch (e: Exception) {
			null
		}
	}

	fun toDateTime(value: String?, formats: List<String>): LocalDateTime? {
		return value?.let {
			formats.stream()
				.map { format -> toDateTime(it, format) }
				.filter { it != null }
				.findFirst()
				.orElse(null)
		}
	}

	private fun toTime(value: String, format: String): LocalTime? {
		return try {
			LocalTime.parse(value, getFormatter(format))
		} catch (e: Exception) {
			null
		}
	}

	fun toTime(value: String?, formats: List<String>): LocalTime? {
		return value?.let {
			formats.stream()
				.map { format -> toTime(it, format) }
				.filter { it != null }
				.findFirst()
				.orElse(null)
		}
	}

	fun isBefore(first: LocalTime, second: LocalTime, equationAllowed: Boolean): Boolean {
		return if (equationAllowed) {
			first.isBefore(second)
		} else {
			first == second || first.isBefore(second)
		}
	}

	fun isAfter(first: LocalTime, second: LocalTime, equationAllowed: Boolean): Boolean {
		return if (equationAllowed) {
			first.isAfter(second)
		} else {
			first == second || first.isAfter(second)
		}
	}

	fun isBefore(first: LocalDate, second: LocalDate, equationAllowed: Boolean): Boolean {
		return if (equationAllowed) {
			first.isBefore(second)
		} else {
			first == second || first.isBefore(second)
		}
	}

	fun isAfter(first: LocalDate, second: LocalDate, equationAllowed: Boolean): Boolean {
		return if (equationAllowed) {
			first.isAfter(second)
		} else {
			first == second || first.isAfter(second)
		}
	}
}