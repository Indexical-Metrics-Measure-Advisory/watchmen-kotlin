package com.matrdata.watchmen.model.runtime

import com.matrdata.watchmen.model.admin.PipelineTriggerType
import com.matrdata.watchmen.model.common.PipelineTriggerTraceId
import com.matrdata.watchmen.model.common.TenantId

sealed interface PipelineTriggerData {
	// topic name
	var code: String?

	// current data
	var data: Map<String, Any>?
	var triggerType: PipelineTriggerType?

	// pass tenant id when use super admin
	var tenantId: TenantId?

	// user given trace id, typically leave it as none
	var traceId: PipelineTriggerTraceId?
}

data class StandardPipelineTriggerData(
	override var code: String? = null,
	override var data: Map<String, Any>? = null,
	override var triggerType: PipelineTriggerType? = PipelineTriggerType.INSERT,
	override var tenantId: TenantId? = null,
	override var traceId: PipelineTriggerTraceId? = null,
) : PipelineTriggerData

data class PipelineTriggerDataWithPAT(
	override var code: String? = null,
	override var data: Map<String, Any>? = null,
	override var triggerType: PipelineTriggerType? = PipelineTriggerType.INSERT,
	override var tenantId: TenantId? = null,
	override var traceId: PipelineTriggerTraceId? = null,
	var pat: String? = null,
) : PipelineTriggerData

data class PipelineTriggerResult(
	var received: Boolean = true,
	var traceId: PipelineTriggerTraceId,
	// id of trigger data, type must be str since length of value beyonds the limitation of serialization of javascript json number
	var internalDataId: String
)
