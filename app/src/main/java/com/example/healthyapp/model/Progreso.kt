package com.example.healthyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progreso")
data class Progreso(

    @PrimaryKey(autoGenerate = true)
    val idProgreso: Int = 0,

    val pesoCorporal: Double,

    val grasaCorporal: Double,

    val pecho: Double,

    val cintura: Double,

    val cadera: Double,

    val bicepsIzq: Double,

    val bicepsDer: Double,

    val musloIzq: Double,

    val musloDer: Double,

    val fecha: String
)