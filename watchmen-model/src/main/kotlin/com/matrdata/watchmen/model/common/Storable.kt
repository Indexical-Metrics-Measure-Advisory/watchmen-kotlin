package com.matrdata.watchmen.model.common

import java.time.LocalDateTime

interface Storable : DataModel

interface Auditable : Storable {
	var createdAt: LocalDateTime?
	var createdBy: UserId?
	var lastModifiedAt: LocalDateTime?
	var lastModifiedBy: UserId?
}

interface OptimisticLock : Storable {
	var version: Int?
}

interface LastVisit : Storable {
	var lastVisitTime: LocalDateTime?
}