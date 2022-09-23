package com.matrdata.watchmen.pipeline.kernel.runnable

import com.matrdata.watchmen.data.kernel.schema.TopicSchema
import com.matrdata.watchmen.pipeline.kernel.compiled.PipelineTrigger

typealias CreatePipelineTask = (TopicSchema, PipelineTrigger) -> Unit