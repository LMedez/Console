package com.luc.domain

import com.luc.common.entities.CalderaEntity
import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto
import com.luc.common.model.Settings
import kotlinx.coroutines.flow.Flow

interface DomainRepository {
    suspend fun getCalderas(): List<Caldera>
    suspend fun getRepuestos(): List<Repuesto>
    fun getSettings() : Flow<Settings>
}