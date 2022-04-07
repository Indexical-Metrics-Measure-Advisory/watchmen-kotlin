package com.matrdata.meta.base

import com.matrdata.auth.Principal
import com.matrdata.model.base.Auditable
import com.matrdata.model.base.OptimisticLock
import com.matrdata.model.base.Storable
import com.matrdata.storage.*
import java.util.*

abstract class StorableStorageService(
    storage: TransactionalStorage, principal: Principal?, snowflake: Snowflake
) : StorageService(storage, principal, snowflake) {
    protected fun now(): Date {
        return Date()
    }

    protected fun tryToPrepareAuditableOnCreate(storable: Storable) {
        if (storable is Auditable) {
            val now = this.now()
            storable.createdAt = now
            storable.createdBy = this.getPrincipal()?.userId
            storable.lastModifiedAt = now
            storable.lastModifiedBy = this.getPrincipal()?.userId
        }
    }

    protected fun tryToPrepareOptimisticLockOnCreate(storable: Storable) {
        if (storable is OptimisticLock) {
            storable.version = 1
        }
    }

    protected fun tryToPrepareOnCreate(storable: Storable) {
        this.tryToPrepareAuditableOnCreate(storable)
        this.tryToPrepareOptimisticLockOnCreate(storable)
    }

    abstract fun getEntityName(): EntityName

    abstract fun getEntityShaper(): EntityShaper

    fun getEntityHelper(): EntityHelper {
        return EntityHelper(name = this.getEntityName(), shaper = this.getEntityShaper())
    }

    fun create(storable: Storable): Storable {
        this.tryToPrepareOnCreate(storable)
        this.getStorage().insertOne(storable, this.getEntityHelper())
        return storable
    }
}