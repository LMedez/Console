package com.luc.common.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.luc.common.entities.CalderaEntity
import com.luc.common.entities.RepuestoEntity
import com.luc.common.model.Caldera

data class CalderaWithRepuestos(
    @Embedded
    val caldera: CalderaEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "calderaId"
    )
    val repuestos: List<RepuestoEntity>
)