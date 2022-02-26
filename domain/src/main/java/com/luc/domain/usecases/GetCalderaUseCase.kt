package com.luc.domain.usecases

import com.luc.domain.DomainRepository

class GetCalderaUseCase(private val domainRepository: DomainRepository) {
    suspend fun getCalderas() = domainRepository.getCalderas()
    suspend fun getRepuestos() = domainRepository.getRepuestos()
}