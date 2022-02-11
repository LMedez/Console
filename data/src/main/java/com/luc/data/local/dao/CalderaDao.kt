package com.luc.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.luc.common.entities.CalderaEntity
import com.luc.common.entities.relations.CalderaWithRepuestos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class CalderaDao : BaseDao<CalderaEntity> {
    @Query("SELECT * FROM CalderaEntity WHERE id = :id")
    abstract suspend fun getCalderaById(id: Long): CalderaEntity

    @Query("SELECT * FROM CalderaEntity")
    abstract fun getCalderas(): Flow<List<CalderaEntity>>

    @Query("SELECT * FROM CalderaEntity")
    abstract suspend fun getCalderaWithRepuestos(): List<CalderaWithRepuestos>

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM CalderaEntity WHERE id = :id")
    protected abstract fun getUserById(id: String): Flow<CalderaEntity>

    fun getDistinctUserById(id: String):
            Flow<CalderaEntity> = getUserById(id).distinctUntilChanged()
}
