package com.matrdata.watchmen.data.kernel.storage

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.model.admin.Topic

/**
 * topic data service
 */
abstract class TopicDataService(val topic: Topic, val principal: Principal) {
	fun findById(id: Long): Map<String, Any?> {
		TODO("Not yet implemented")
	}
}