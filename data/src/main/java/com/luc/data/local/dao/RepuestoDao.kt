package com.luc.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.luc.common.entities.CalderaEntity
import com.luc.common.entities.RepuestoEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RepuestoDao : BaseDao<RepuestoEntity> {

    @Query("SELECT * FROM RepuestoEntity")
    abstract suspend fun getRepuestos(): List<RepuestoEntity>

}