package com.luc.data.local

import com.luc.common.entities.asRepuesto
import com.luc.common.entities.asSettings
import com.luc.common.model.Caldera
import com.luc.data.local.dao.CalderaDao
import com.luc.data.local.dao.RepuestoDao
import com.luc.data.local.dao.SettingsDao
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class LocalDataSource(private val calderaDao: CalderaDao, private val repuestoDao: RepuestoDao, private val settingsDao: SettingsDao) {

    internal suspend fun getCalderas() = calderaDao.getCalderaWithRepuestos()
        .map { calderaWithRepuestos ->
            Caldera(
                calderaWithRepuestos.caldera.id,
                calderaWithRepuestos.caldera.caldera,
                calderaWithRepuestos.repuestos.map { it.asRepuesto() })
        }

    internal fun getSettings() = settingsDao.getSettings().map { it.asSettings() }

}