package com.matrdata.watchmen.model.common

typealias DatasetCellValue = Any?
typealias DatasetRow = List<DatasetCellValue>
typealias Dataset = List<DatasetRow>

sealed interface NamedDataset {
	var columns: List<String>?
	var data: Dataset
}

data class StandardNamedDataset(
	override var columns: List<String>? = null,
	override var data: Dataset,
) : NamedDataset


sealed interface Pageable {
	var pageNumber: Int
	var pageSize: Int
}

data class NamedDatasetPage(
	override var columns: List<String>? = null,
	override var data: Dataset,
	override var pageNumber: Int,
	override var pageSize: Int,
	var pageCount: Int,
	var itemCount: Int,
) : NamedDataset, Pageable

sealed class DatasetPage<R>(
	open var data: List<R>,
	override var pageNumber: Int,
	override var pageSize: Int,
	open var pageCount: Int,
	open var itemCount: Int
) : Pageable

/** dataset row with name */
typealias DatasetRowOnMap = Map<String, DatasetCellValue>
typealias DatasetOnMap = List<DatasetRowOnMap>

data class DatasetPageOnRow(
	override var data: List<DatasetOnMap>,
	override var pageNumber: Int,
	override var pageSize: Int,
	override var pageCount: Int,
	override var itemCount: Int
) : DatasetPage<DatasetOnMap>(
	data = data,
	pageNumber = pageNumber,
	pageSize = pageSize,
	pageCount = pageCount,
	itemCount = itemCount
)

typealias DatasetRowOnModel = Map<String, DataModel>
typealias DatasetOnModel = List<DatasetRowOnModel>

data class DatasetPageOnModel(
	override var data: List<DatasetOnModel>,
	override var pageNumber: Int,
	override var pageSize: Int,
	override var pageCount: Int,
	override var itemCount: Int
) : DatasetPage<DatasetOnModel>(
	data = data,
	pageNumber = pageNumber,
	pageSize = pageSize,
	pageCount = pageCount,
	itemCount = itemCount
)
