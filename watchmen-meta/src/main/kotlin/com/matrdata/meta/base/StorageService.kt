package com.matrdata.meta.base

import com.matrdata.auth.Principal
import com.matrdata.storage.Snowflake
import com.matrdata.storage.TransactionalStorage

open class StorageService(
    private val storage: TransactionalStorage,
    private var principal: Principal? = null,
    private var snowflake: Snowflake? = null
) {
    fun withPrincipal(principal: Principal): StorageService {
        this.principal = principal
        return this
    }

    fun withSnowflake(snowflake: Snowflake): StorageService {
        this.snowflake = snowflake
        return this
    }

    fun begin() {
        this.storage.begin()
    }

    fun commit() {
        this.storage.commitAndClose()
    }

    fun rollback() {
        this.storage.rollbackAndClose()
    }

    fun close() {
        this.storage.close()
    }

    protected fun getStorage(): TransactionalStorage {
        return this.storage
    }

    protected fun getPrincipal(): Principal? {
        return this.principal
    }

    protected fun getSnowflake(): Snowflake? {
        return this.snowflake
    }
}
