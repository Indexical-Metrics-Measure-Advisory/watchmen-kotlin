package com.matrdata.storage

import com.matrdata.model.base.Storable

interface EntityShaper {
    fun serialize(storable: Storable): EntityRow

    fun deserialize(row: EntityRow): Storable
}