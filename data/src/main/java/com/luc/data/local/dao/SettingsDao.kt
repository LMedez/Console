package com.luc.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luc.common.entities.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(settingsEntity: SettingsEntity): Long

    @Query("UPDATE SettingsEntity SET applyIva = :applyIva")
    abstract suspend fun updateApplyIva(applyIva: Boolean)

    @Query("SELECT * FROM SettingsEntity WHERE id = 1")
    abstract fun getSettings(): Flow<SettingsEntity>
}