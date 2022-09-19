package com.matrdata.watchmen.model.system

import com.matrdata.watchmen.model.common.*
import java.time.LocalDateTime

enum class DataSourceType(val code: String) {
	MYSQL("mysql"),
	ORACLE("oracle"),
	MONGODB("mongodb"),
	MSSQL("mssql"),
	POSTGRESQL("postgresql"),
	OSS("oss"),
	S3("s3"),
}

data class DataSourceParam(
	var name: String? = null,
	var value: String? = null,
) : DataModel

data class DataSource(
	var dataSourceId: DataSourceId? = null,
	var dataSourceCode: String? = null,
	var dataSourceType: DataSourceType? = null,
	var host: String? = null,
	var port: String? = null,
	var username: String? = null,
	var password: String? = null,
	var name: String? = null,
	var urlval: String? = null,
	var params: List<DataSourceParam>? = null,
	override var tenantId: TenantId? = null,
	override var version: Int? = 1,
	override var createdAt: LocalDateTime? = null,
	override var createdBy: UserId? = null,
	override var lastModifiedAt: LocalDateTime? = null,
	override var lastModifiedBy: UserId? = null
) : TenantBasedTuple, OptimisticLock