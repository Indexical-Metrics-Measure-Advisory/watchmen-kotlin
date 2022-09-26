package com.matrdata.watchmen.data.kernel.meta

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.common.ExternalWriterId
import com.matrdata.watchmen.model.system.ExternalWriter

class ExternalWriterMetaService(private val principal: Principal) {
	fun findById(writerId: ExternalWriterId): ExternalWriter? {
		TODO("Not yet implemented")
	}
}