package com.matrdata.model.common

/** should be a value, like Int, Date, etc.; not a struct such as list or map */
typealias DataResultSetCell = Any
typealias DataResultSetRow = List<DataResultSetCell>
typealias  DataResultSet = List<DataResultSetRow>


class DataResult(
	var columns: List<String>? = null,
	var data: DataResultSet? = null
)