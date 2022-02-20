package com.luc.data

import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto
import com.luc.common.model.Settings
import com.luc.data.local.LocalDataSource
import com.luc.domain.DomainRepository
import kotlinx.coroutines.flow.Flow

class DomainRepositoryImpl(
//    private val firestoreData: FirestoreData,
    private val localDataSource: LocalDataSource
) : DomainRepository {

    override suspend fun getCalderas(): List<Caldera> {
        return localDataSource.getCalderas()
    }

    override suspend fun getRepuestos() = localDataSource.getRepuestos()

    override fun getSettings(): Flow<Settings> {
        return localDataSource.getSettings()
    }
}