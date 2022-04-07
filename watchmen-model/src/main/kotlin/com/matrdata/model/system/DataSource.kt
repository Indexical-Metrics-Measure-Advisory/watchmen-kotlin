package com.matrdata.model.system

import com.matrdata.model.base.*
import java.util.*

class DataSourceParam(
	var name: String? = null,
	var value: String? = null
)


enum class DataSourceType(val code: String) {
	MYSQL("mysql"),
	ORACLE("oracle"),
	MONGODB("mongodb"),
	MSSQL("mssql")
}

class DataSource(
	var dataSourceId: DataSourceId? = null,
	var dataSourceCode: String? = null,
	var dataSourceType: DataSourceType? = null,
	var host: String? = null,
	var port: String? = null,
	var username: String? = null,
	var password: String? = null,
	var name: String? = null,
	var url: String? = null,
	var params: List<DataSourceParam>? = mutableListOf(),
	override var createdAt: Date? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: Date? = null,
	override var lastModifiedBy: UserId? = null,
	override var version: Int? = null,
	override var tenantId: TenantId? = null
) : TenantBasedTuple, OptimisticLock