package com.example.healthyapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthyapp.model.Ejercicio

@Dao
interface EjercicioDao {

    @Insert
    suspend fun insertarEjercicio(ejercicio: Ejercicio)

    @Query("SELECT * FROM ejercicios")
    suspend fun obtenerEjercicios(): List<Ejercicio>

    @Query("SELECT * FROM ejercicios WHERE idRutina = :idRutina")
    suspend fun obtenerEjerciciosPorRutina(idRutina: Int): List<Ejercicio>

    @Query("SELECT * FROM ejercicios WHERE idEjercicio = :idEjercicio")
    suspend fun obtenerEjercicioPorId(idEjercicio: Int): Ejercicio?

    @Query("""
        UPDATE ejercicios
        SET completado = 1,
            fechaCompletado = :fecha
        WHERE idEjercicio = :idEjercicio
    """)
    suspend fun marcarEjercicioCompletado(
        idEjercicio: Int,
        fecha: String
    )

    @Query("SELECT * FROM ejercicios WHERE completado = 1")
    suspend fun obtenerEjerciciosCompletados(): List<Ejercicio>
}