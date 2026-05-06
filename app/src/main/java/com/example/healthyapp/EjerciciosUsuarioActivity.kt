package com.example.healthyapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import kotlinx.coroutines.launch

class EjerciciosUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicios_usuario)

        val tvTituloEjercicios = findViewById<TextView>(R.id.tvTituloEjercicios)
        val tvDescripcionRutina = findViewById<TextView>(R.id.tvDescripcionRutinaUsuario)
        val layoutEjercicios = findViewById<LinearLayout>(R.id.layoutEjercicios)

        val idRutina = intent.getIntExtra("idRutina", 1)
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val rutina = db.rutinaDao().obtenerRutinaPorId(idRutina)

            if (rutina != null) {
                tvTituloEjercicios.text = "${rutina.nombre} (${rutina.tipoRutina})"
                tvDescripcionRutina.text = rutina.descripcion
            }

            val ejercicios = db.ejercicioDao().obtenerEjerciciosPorRutina(idRutina)

            if (ejercicios.isEmpty()) {
                val texto = TextView(this@EjerciciosUsuarioActivity)
                texto.text = "No hay ejercicios en esta rutina"
                texto.textSize = 16f
                layoutEjercicios.addView(texto)
            } else {
                for (ejercicio in ejercicios) {
                    val texto = TextView(this@EjerciciosUsuarioActivity)

                    texto.text = """
                        ${ejercicio.nombre}
                        Series: ${ejercicio.series}
                        Repeticiones: ${ejercicio.repeticiones}
                        Duración: ${ejercicio.duracion} minutos
                        Dificultad: ${ejercicio.dificultad}
                    """.trimIndent()

                    texto.textSize = 16f
                    texto.setPadding(0, 0, 0, 24)

                    texto.setOnClickListener {
                        val intent = Intent(
                            this@EjerciciosUsuarioActivity,
                            EjercicioDetalleActivity::class.java
                        )

                        intent.putExtra("idEjercicio", ejercicio.idEjercicio)
                        startActivity(intent)
                    }

                    layoutEjercicios.addView(texto)
                }
            }
        }
    }
}