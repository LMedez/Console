package com.luc.domain

import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto
import com.luc.common.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDomainRepository : DomainRepository {
    val list = mutableListOf(Caldera(caldera = "caldera", repuestos = listOf(
        Repuesto(
            calderaId = "wherever",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 153.88644546,
            precioPublico = 12.588888
        ),
        Repuesto(
            calderaId = "wherever",

            descripcion = "descripcion",
            codigo = "453215",
            precioService = 53.8864444546,
            precioPublico = 329.854446
        ),
        Repuesto(
            calderaId = "wherever",

            descripcion = "descripcion",
            codigo = "453215",
            precioService = 15.884546,
            precioPublico = 125.56666
        ),
        Repuesto(
            calderaId = "wherever",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 534.8644546,
            precioPublico = 999.02
        ),
        Repuesto(
            calderaId = "wherever",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 153.88644546,
            precioPublico = 588.56545
        ),
        Repuesto(
            calderaId = "wherever",
            descripcion = "descripcion",
            codigo = "453215",
            precioService = 153.88644546,
            precioPublico = 255.51
        ),
    )))

    override suspend fun getCalderas(): List<Caldera> = list

    override fun getSettings(): Flow<Settings> {
        return flow { Settings(applyIva = true) }
    }
}