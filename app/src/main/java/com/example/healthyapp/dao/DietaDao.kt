package com.example.healthyapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthyapp.model.Dieta

@Dao
interface DietaDao {

    @Insert
    suspend fun insertarDieta(dieta: Dieta)

    @Query("SELECT * FROM dietas WHERE idUsuario = :idUsuario ORDER BY idDieta DESC LIMIT 1")
    suspend fun obtenerDietaUsuario(idUsuario: Int): Dieta?
}