package com.example.healthyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dietas")
data class Dieta(
    @PrimaryKey(autoGenerate = true)
    val idDieta: Int = 0,
    val nombre: String,
    val descripcion: String,
    val archivo: String?,
    val idUsuario: Int = 1
)