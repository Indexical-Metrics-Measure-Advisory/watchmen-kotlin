package com.matrdata.model.system

import com.matrdata.model.base.Storable
import java.util.*

class CompetitiveWorker(
	var ip: String? = null,
	var processId: String? = null,
	var dataCenterId: Int? = null,
	var workerId: Int? = null,
	var registeredAt: Date? = null,
	var lastBeatAt: Date? = null
) : Storable