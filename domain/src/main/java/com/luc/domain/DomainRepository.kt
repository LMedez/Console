package com.luc.domain

import com.luc.common.entities.CalderaEntity
import com.luc.common.model.Caldera
import kotlinx.coroutines.flow.Flow

interface DomainRepository {
    suspend fun getCalderas(): List<Caldera>
}