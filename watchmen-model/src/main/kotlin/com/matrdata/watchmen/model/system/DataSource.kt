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
)

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
	override var tenantId: TenantId?,
	override var version: Int?,
	override var createdAt: LocalDateTime?,
	override var createdBy: UserId?,
	override var lastModifiedAt: LocalDateTime?,
	override var lastModifiedBy: UserId?
) : TenantBasedTuple, OptimisticLock