package com.luc.common.utils

import kotlin.math.round

fun Double.round(decimals: Int = 2): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun addPercent(number: Double, percent: Int): Double {
    val applyPercent: Double =
        number + (number * percent) / 100
    return applyPercent.round()
}

