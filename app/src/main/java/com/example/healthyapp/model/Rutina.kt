package com.example.healthyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutinas")
data class Rutina(
    @PrimaryKey(autoGenerate = true)
    val idRutina: Int = 0,
    val nombre: String,
    val descripcion: String,
    val tipoRutina: String,
    val idUsuario: Int = 1
)