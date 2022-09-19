package com.matrdata.watchmen.model.common

class Pagination {
}

interface Pageable {
	var pageNumber: Int
	var pageSize: Int
}