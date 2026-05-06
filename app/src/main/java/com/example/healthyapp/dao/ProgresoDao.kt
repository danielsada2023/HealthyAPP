package com.example.healthyapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthyapp.model.Progreso

@Dao
interface ProgresoDao {

    @Insert
    suspend fun insertarProgreso(progreso: Progreso)

    @Query("SELECT * FROM progreso ORDER BY idProgreso DESC")
    suspend fun obtenerProgresos(): List<Progreso>
}