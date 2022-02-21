package com.luc.domain.usecases

import com.luc.common.model.Settings
import com.luc.domain.DomainRepository

class GetSettingsUseCase(private val domainRepository: DomainRepository) {
    fun getSettings() = domainRepository.getSettings()
    suspend fun updateSettings(settings: Settings) = domainRepository.updateSettings(settings)
}