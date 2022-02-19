package com.luc.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luc.common.model.Settings

@Entity
data class SettingsEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 1,
    val applyIva: Boolean = true,
    val applyGain: Boolean = true,
    val gainValue: Int = 35,
    val dolarValue: Int = 112
)

fun SettingsEntity.asSettings() = Settings(
    id = this.id,
    applyIva = this.applyIva,
    applyGain = this.applyGain,
    gainValue = this.gainValue,
    dolarValue = this.dolarValue
)
