package com.luc.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luc.common.model.Repuesto

@Entity
data class RepuestoEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val calderaId: String,
    val descripcion: String,
    val codigo: String,
    val precioService: Double,
    val precioPublico: Double,
)

fun RepuestoEntity.asRepuesto() = Repuesto(
    this.id,
    this.calderaId,
    this.descripcion,
    this.codigo,
    this.precioService,
    this.precioPublico
)

