package com.matrdata.watchmen.utils

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun LocalDateTime.spentInMs(): Long {
	return this.until(LocalDateTime.now(), ChronoUnit.MILLIS)
}