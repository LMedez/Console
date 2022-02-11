package com.luc.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto

@Entity
data class CalderaEntity(@PrimaryKey(autoGenerate = false) val id: String, val caldera: String)
