package com.luc.data.local

import com.luc.common.entities.asRepuesto
import com.luc.common.model.Caldera
import com.luc.data.local.dao.CalderaDao
import com.luc.data.local.dao.RepuestoDao

class LocalDataSource(private val calderaDao: CalderaDao, private val repuestoDao: RepuestoDao) {

    internal suspend fun getCalderas() = calderaDao.getCalderaWithRepuestos()
        .map { calderaWithRepuestos ->
            Caldera(
                calderaWithRepuestos.caldera.id,
                calderaWithRepuestos.caldera.caldera,
                calderaWithRepuestos.repuestos.map { it.asRepuesto() })
        }

}