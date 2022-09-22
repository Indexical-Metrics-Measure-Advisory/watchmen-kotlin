package com.matrdata.watchmen.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun String?.throwIfBlank(block: String?.() -> String): String {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	return if (ValueUtils.isBlank(this)) {
		throw IllegalArgumentException(block())
	} else {
		this!!
	}
}

@OptIn(ExperimentalContracts::class)
inline fun String?.throwIfBlank2(block: String?.() -> Throwable): String {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	return if (ValueUtils.isBlank(this)) {
		throw block()
	} else {
		this!!
	}
}

fun String?.toDate(formats: List<String>): LocalDate? {
	return DateTimeUtils.toDate(this, formats)
}

fun String?.toDateTime(formats: List<String>): LocalDateTime? {
	return DateTimeUtils.toDateTime(this, formats);
}

fun String?.toTime(formats: List<String>): LocalTime? {
	return DateTimeUtils.toTime(this, formats)
}