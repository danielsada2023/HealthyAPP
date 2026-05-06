package com.example.healthyapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthyapp.model.Rutina

@Dao
interface RutinaDao {

    @Insert
    suspend fun insertarRutina(rutina: Rutina): Long

    @Query("SELECT * FROM rutinas WHERE idUsuario = :idUsuario")
    suspend fun obtenerRutinasUsuario(idUsuario: Int): List<Rutina>

    @Query("SELECT * FROM rutinas WHERE idRutina = :idRutina")
    suspend fun obtenerRutinaPorId(idRutina: Int): Rutina?
}