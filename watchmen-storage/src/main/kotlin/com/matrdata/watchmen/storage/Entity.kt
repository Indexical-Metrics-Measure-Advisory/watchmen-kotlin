package com.matrdata.watchmen.storage

import com.matrdata.watchmen.model.common.Storable

typealias EntityName = String
typealias EntityColumnName = String
typealias EntityColumnValue = Any?
typealias EntityRow = Map<EntityColumnName, EntityColumnValue>

/**
 * serialize/deserialize between object and entity in storage
 */
interface EntityShaper<S : Storable> {
	fun serialize(storable: S): EntityRow
	fun deserialize(row: EntityRow): S
}

open class EntityHelper(val name: EntityName, val shaper: EntityShaper<out Storable>)

class EntityIdHelper(
	name: EntityName, shaper: EntityShaper<out Storable>, val idColumnName: EntityColumnName
) : EntityHelper(name, shaper)

typealias EntityCriteria = CriteriaStatement

typealias EntityUpdate = Map<EntityColumnName, EntityColumnValue>

class EntityUpdater(
	name: EntityName, shaper: EntityShaper<out Storable>, criteria: EntityCriteria? = null, update: EntityUpdate
) : EntityHelper(name, shaper)
