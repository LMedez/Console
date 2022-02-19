package com.luc.common.model

import android.os.Parcelable
import android.util.Log
import androidx.room.Ignore
import com.luc.common.entities.CalderaEntity
import com.luc.common.utils.addPercent
import kotlinx.parcelize.IgnoredOnParcel
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
    val calderaId: String,
    val descripcion: String,
    val codigo: String,
    var precioService: Double,
    var precioPublico: Double,
) : Parcelable {


    @IgnoredOnParcel
    var settings: Settings? = null

    @IgnoredOnParcel
    val gainValue: Int
        get() {
            return settings?.gainValue ?: 0
        }

    @IgnoredOnParcel
    var calderaName: String = ""

    @IgnoredOnParcel
    val dolarValue: Int
        get() {
            return settings?.dolarValue ?: 0
        }

    @IgnoredOnParcel
    val _precioService: Double
        get() {
            return settings?.let {
                if (it.applyIva)
                    addPercent(precioService, 21)
                else precioService
            } ?: precioService
        }

    @IgnoredOnParcel
    val precioServiceInARS: Int
        get() {
            return settings?.let {
                if (it.applyIva)
                    addPercent((precioService * it.dolarValue), 21).toInt()
                else (precioService * it.dolarValue).toInt()
            } ?: precioService.toInt()
        }

    @IgnoredOnParcel
    val precioPublicoInARS: Int
        get() {
            return settings?.let {
                if (it.applyGain)
                    addPercent((precioPublico * it.dolarValue), it.gainValue).toInt()
                else (precioPublico * it.dolarValue).toInt()
            } ?: precioPublico.toInt()
        }
}

fun Caldera.asCalderaEntity(): CalderaEntity = CalderaEntity(this.id, this.caldera)

