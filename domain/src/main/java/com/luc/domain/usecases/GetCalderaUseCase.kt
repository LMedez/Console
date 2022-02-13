package com.luc.domain.usecases

import android.util.Log
import com.luc.common.model.Caldera
import com.luc.domain.DomainRepository

class GetCalderaUseCase(private val domainRepository: DomainRepository) {
    suspend fun getCalderas(): List<Caldera> {
        val calderaList = domainRepository.getCalderas()
        calderaList.map {
            it.repuestos.map { repuesto ->
                repuesto.precioService = String.format(
                    "%.2f",
                    repuesto.precioService.toDouble()
                )
                repuesto.precioPublico = String.format(
                    "%.2f",
                    repuesto.precioPublico.toDouble()
                )
            }
        }

        return calderaList
    }
}