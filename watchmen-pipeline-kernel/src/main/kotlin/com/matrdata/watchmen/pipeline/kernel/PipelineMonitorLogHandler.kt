package com.matrdata.watchmen.pipeline.kernel

import com.matrdata.watchmen.model.runtime.monitor.PipelineMonitorLog

typealias Asynchronized = Boolean
typealias PipelineMonitorLogHandle = (PipelineMonitorLog, Asynchronized) -> Unit
