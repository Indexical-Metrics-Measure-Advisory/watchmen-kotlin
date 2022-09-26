package com.matrdata.watchmen.pipeline.kernel

//DECRYPT_FACTOR_VALUE: bool = False
//PIPELINE_PARALLEL_ACTIONS_IN_LOOP_UNIT: bool = False
//PIPELINE_PARALLEL_ACTIONS_COUNT: int = 8
//PIPELINE_PARALLEL_ACTIONS_DASK_THREADS_PER_WORK: int = 1
//PIPELINE_PARALLEL_ACTIONS_DASK_TEMP_DIR: Optional[str] = None
//PIPELINE_PARALLEL_ACTIONS_DASK_USE_PROCESS: bool = True
//PIPELINE_STANDARD_EXTERNAL_WRITER: bool = True
//PIPELINE_ELASTIC_SEARCH_EXTERNAL_WRITER: bool = False
//PIPELINE_UPDATE_RETRY: bool = True  # enable pipeline update retry if it is failed on optimistic lock
//PIPELINE_UPDATE_RETRY_TIMES: int = 3  # optimistic lock retry times
//PIPELINE_UPDATE_RETRY_INTERVAL: int = 10  # retry interval in milliseconds
//PIPELINE_UPDATE_RETRY_FORCE: bool = True  # enable force retry after all retries failed
//PIPELINE_ASYNC_HANDLE_MONITOR_LOG: bool = True  # handle monitor log (might with pipelines) asynchronized

fun askIsAsyncHandlePipelineMonitorLog(): Boolean {
	return true
}

fun askIsParallelActionsInLoopUnit(): Boolean {
	return false
}