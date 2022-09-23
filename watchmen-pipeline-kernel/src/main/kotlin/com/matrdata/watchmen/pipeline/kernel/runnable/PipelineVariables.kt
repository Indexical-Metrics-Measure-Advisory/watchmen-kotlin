package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.model.admin.Topic

class PipelineVariables(
	private val previousData: Map<String, Any>?, private val currentData: Map<String, Any>?,
	private val topic: Topic
) {
//	private val variables: Map<String, Any?> = mutableMapOf()
//
//	// only variables from trigger data will record its factor name here
//	// key is variable key, value is factor name
//	private val variablesFrom: Map<String, OneOf2<Topic, Factor>> = mutableMapOf()
}