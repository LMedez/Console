package com.luc.common.model

import com.luc.common.entities.SettingsEntity

data class Settings(
    val id: Long = 1,
    val applyIva: Boolean = true,
    val applyGain: Boolean = true,
    val gainValue: Int = 35,
    val dolarValue: Int = 112
)

fun Settings.asSettingsEntity() =
    SettingsEntity(this.id, this.applyIva, this.applyGain, this.gainValue, this.dolarValue)
