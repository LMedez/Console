package com.luc.domain.usecases

import com.luc.domain.DomainRepository

class GetSettingsUseCase(private val domainRepository: DomainRepository) {
    fun getSettings() = domainRepository.getSettings()
}