package com.example.healthyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthyapp.dao.DietaDao
import com.example.healthyapp.dao.EjercicioDao
import com.example.healthyapp.dao.ProgresoDao
import com.example.healthyapp.dao.RutinaDao
import com.example.healthyapp.model.Dieta
import com.example.healthyapp.model.Ejercicio
import com.example.healthyapp.model.Progreso
import com.example.healthyapp.model.Rutina

@Database(
    entities = [
        Rutina::class,
        Ejercicio::class,
        Dieta::class,
        Progreso::class
    ],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rutinaDao(): RutinaDao
    abstract fun ejercicioDao(): EjercicioDao
    abstract fun dietaDao(): DietaDao
    abstract fun progresoDao(): ProgresoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "healthyapp_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}