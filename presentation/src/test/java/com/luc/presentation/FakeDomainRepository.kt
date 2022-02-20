package com.luc.presentation

import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto
import com.luc.common.model.Settings
import com.luc.domain.DomainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDomainRepository : DomainRepository {
    val calderaList = listOf(
        Caldera(id = "123456", caldera = "EGIS"),
        Caldera(id = "7856", caldera = "Class"),
        Caldera(id = "4568", caldera = "Class X")
    )
    val repuestos = listOf(
        Repuesto(
            calderaId = "123456",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 153.88644546,
            precioPublico = 12.588888
        ),
        Repuesto(
            calderaId = "123456",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 53.8864444546,
            precioPublico = 329.854446
        ),
        Repuesto(
            calderaId = "123456",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 15.884546,
            precioPublico = 125.56666
        ),
        Repuesto(
            calderaId = "7856",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 534.8644546,
            precioPublico = 999.02
        ),
        Repuesto(
            calderaId = "7856",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 153.88644546,
            precioPublico = 588.56545
        ),
        Repuesto(
            calderaId = "4568",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 153.88644546,
            precioPublico = 255.51
        ),
    )

    override suspend fun getCalderas(): List<Caldera> = calderaList

    override suspend fun getRepuestos(): List<Repuesto> {
        return repuestos
    }

    override fun getSettings(): Flow<Settings> {
        return flow { Settings() }
    }
}