package com.matrdata.storage

import com.matrdata.model.base.Storable

interface TransactionalStorage : Storage {
    fun begin(): Nothing
    fun commitAndClose(): Nothing
    fun rollbackAndClose(): Nothing
    fun insertOne(row: Storable, entityHelper: EntityHelper)
}