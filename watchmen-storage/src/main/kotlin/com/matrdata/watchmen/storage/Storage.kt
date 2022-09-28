package com.matrdata.watchmen.storage

interface Storage {
	fun connect()
	fun close()
}

interface TransactionalStorage : Storage {
	fun begin()

	/**
	 * 1. commit successfully -> close
	 * 2. commit failed -> throw exception
	 */
	fun commitAndClose()

	/**
	 * 1. rollback successfully -> close
	 * 2. rollback failed -> close
	 */
	fun rollbackAndClose()
}
