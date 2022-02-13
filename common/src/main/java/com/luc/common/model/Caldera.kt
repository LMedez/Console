package com.luc.common.model

import android.os.Parcelable
import com.luc.common.entities.CalderaEntity
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Caldera(
    val id: String = UUID.randomUUID().toString(),
    val caldera: String,
    val repuestos: List<Repuesto>
) : Parcelable

@Parcelize
data class Repuesto(
    val id: String = UUID.randomUUID().toString(),
    val descripcion: String,
    val codigo: String,
    var precioService: String,
    var precioPublico: String,
) : Parcelable

fun Caldera.asCalderaEntity(): CalderaEntity = CalderaEntity(this.id, this.caldera)

