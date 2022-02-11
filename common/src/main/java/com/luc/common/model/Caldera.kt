package com.luc.common.model

import com.luc.common.entities.CalderaEntity
import java.util.*

data class Caldera(val id: String = UUID.randomUUID().toString(), val caldera: String, val repuestos: List<Repuesto>)

data class Repuesto(
    val id: String = UUID.randomUUID().toString(),
    val descripcion: String,
    val codigo: String,
    val precioService: String,
    val precioPublico: String,
)
fun Caldera.asCalderaEntity(): CalderaEntity = CalderaEntity(this.id, this.caldera)

