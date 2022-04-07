package com.matrdata.model.base

import java.util.*

interface Auditable : Storable {
    var createdAt: Date?
    var createdBy: UserId?
    var lastModifiedAt: Date?
    var lastModifiedBy: UserId?
}