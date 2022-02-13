package com.luc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luc.common.entities.RepuestoEntity
import com.luc.common.entities.CalderaEntity
import com.luc.common.entities.SettingsEntity
import com.luc.data.local.dao.RepuestoDao
import com.luc.data.local.dao.CalderaDao
import com.luc.data.local.dao.SettingsDao
import java.util.concurrent.Executors

@Database(
    entities = [
        CalderaEntity::class, RepuestoEntity::class, SettingsEntity::class],
    version = 1,
    exportSchema = true
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun calderaDao(): CalderaDao
    abstract fun repuestoDao(): RepuestoDao
    abstract fun settingsDao(): SettingsDao
}
