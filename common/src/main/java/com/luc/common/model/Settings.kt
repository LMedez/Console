package com.luc.common.model

data class Settings(
    private val id: Long = 1,
    val applyIva: Boolean = true,
    val applyGain: Boolean = true,
    val gainValue: Int = 35,
    val dolarValue: Int = 112
)
