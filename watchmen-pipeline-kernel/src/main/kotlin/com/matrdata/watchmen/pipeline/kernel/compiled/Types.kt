package com.matrdata.watchmen.pipeline.kernel.compiled

import com.matrdata.watchmen.auth.Principal
import com.matrdata.watchmen.pipeline.kernel.PipelineVariables

typealias DefJSON = String
typealias PrerequisiteTest = (PipelineVariables, Principal) -> Boolean
