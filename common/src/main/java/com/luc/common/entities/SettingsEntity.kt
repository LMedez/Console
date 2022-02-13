package com.luc.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luc.common.model.Settings

@Entity
data class SettingsEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 1,
    val applyIva: Boolean = true
)

fun SettingsEntity.asSettings() = Settings(applyIva = this.applyIva)
