package com.matrdata.watchmen.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JSON {
	private val mapper = jacksonObjectMapper()

	fun serialize(any: Any?): String? {
		return any?.run { mapper.writeValueAsString(this) }
	}

	fun <X> deserialize(json: String?, cls: Class<X>): X? {
		return json?.run { mapper.readValue(json, cls) }
	}
}

fun <T> T.toJSON(): String {
	return JSON.serialize(this)!!
}

fun <X> String.toModel(cls: Class<X>): X {
	return JSON.deserialize(this, cls)!!
}