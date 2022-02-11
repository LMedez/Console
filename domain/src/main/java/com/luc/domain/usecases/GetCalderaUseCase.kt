package com.luc.domain.usecases

import android.util.Log
import com.luc.common.entities.CalderaEntity
import com.luc.domain.DomainRepository

class GetCalderaUseCase(private val domainRepository: DomainRepository) {
    suspend fun getCalderas() = domainRepository.getCalderas()
}