package com.matrdata.model.base

interface OptimisticLock : Storable {
    var version: Int?
}

