package com.example.healthyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ejercicios")
data class Ejercicio(
    @PrimaryKey(autoGenerate = true)
    val idEjercicio: Int = 0,
    val nombre: String,
    val descripcion: String,
    val repeticiones: Int,
    val series: Int,
    val duracion: Int,
    val dificultad: String,
    val idRutina: Int,
    val imagen: String? = null,
    val video: String? = null,
    var completado: Boolean = false,
    var fechaCompletado: String? = null
)