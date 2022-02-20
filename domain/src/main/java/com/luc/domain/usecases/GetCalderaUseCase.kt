package com.luc.domain.usecases

import android.util.Log
import com.luc.common.model.Caldera
import com.luc.domain.DomainRepository

class GetCalderaUseCase(private val domainRepository: DomainRepository) {
    suspend fun getCalderas() = domainRepository.getCalderas()
    suspend fun getRepuestos() = domainRepository.getRepuestos()
}