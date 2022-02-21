package com.luc.data.local

import android.util.Log
import com.luc.common.entities.asCaldera
import com.luc.common.entities.asRepuesto
import com.luc.common.entities.asSettings
import com.luc.common.model.Caldera
import com.luc.common.model.Settings
import com.luc.common.model.asSettingsEntity
import com.luc.data.local.dao.CalderaDao
import com.luc.data.local.dao.RepuestoDao
import com.luc.data.local.dao.SettingsDao
import com.luc.data.local.dao.insertOrUpdate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class LocalDataSource(
    private val calderaDao: CalderaDao,
    private val repuestoDao: RepuestoDao,
    private val settingsDao: SettingsDao
) {

    internal suspend fun getCalderas() = calderaDao.getCalderas().map { it.asCaldera() }

    internal fun getSettings() = settingsDao.getSettings().map {
        if (it != null) {
            it.asSettings()
        } else Settings()
    }

    internal suspend fun updateSettings(settings: Settings) = settingsDao.insertOrUpdate(settings.asSettingsEntity())

    internal suspend fun getRepuestos() = repuestoDao.getRepuestos().map { it.asRepuesto() }

}