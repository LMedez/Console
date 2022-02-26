package com.luc.common.model

import android.os.Parcelable
import android.util.Log
import androidx.room.Ignore
import com.luc.common.entities.CalderaEntity
import com.luc.common.utils.addPercent
import com.luc.common.utils.round
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Caldera(
    val id: String = UUID.randomUUID().toString(),
    val caldera: String,
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
    var count = 1

    private val precioServiceX: Double
        get() {
            return (precioService * count).round()
        }

    private val precioPublicoX: Double
        get() {
            return (precioPublico * count).round()
        }

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
                    addPercent(precioServiceX, 21)
                else precioServiceX
            } ?: precioServiceX
        }

    val _precioPublico: Double
        get() {
            return precioPublicoX
        }

    @IgnoredOnParcel
    val precioServiceInARS: Int
        get() {
            return settings?.let {
                if (it.applyIva)
                    addPercent((precioServiceX * it.dolarValue), 21).toInt()
                else (precioServiceX * it.dolarValue).toInt()
            } ?: precioServiceX.toInt()
        }

    @IgnoredOnParcel
    val precioPublicoInARS: Int
        get() {
            return settings?.let {
                if (it.applyGain)
                    addPercent((precioPublicoX * it.dolarValue), it.gainValue).toInt()
                else (precioPublicoX * it.dolarValue).toInt()
            } ?: precioPublicoX.toInt()
        }

    fun toEmailString() = "$count ${this.descripcion} - Codigo: ${this.codigo} \n"
}

fun Caldera.asCalderaEntity(): CalderaEntity = CalderaEntity(this.id, this.caldera)

