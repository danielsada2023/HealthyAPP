package com.example.healthyapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import com.example.healthyapp.model.Ejercicio
import com.example.healthyapp.model.Rutina
import kotlinx.coroutines.launch

class RutinaActivity : AppCompatActivity() {

    private val listaEjercicios = mutableListOf<Ejercicio>()

    private lateinit var layoutEjerciciosAnadidos: LinearLayout

    private val lanzarEjercicio =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK && result.data != null) {

                val data = result.data!!

                val ejercicio = Ejercicio(
                    nombre = data.getStringExtra("nombre") ?: "",
                    descripcion = data.getStringExtra("descripcion") ?: "",
                    repeticiones = data.getIntExtra("repeticiones", 0),
                    series = data.getIntExtra("series", 0),
                    duracion = data.getIntExtra("duracion", 0),
                    dificultad = data.getStringExtra("dificultad") ?: "",
                    idRutina = 0,
                    imagen = data.getStringExtra("imagen"),
                    video = data.getStringExtra("video")
                )

                listaEjercicios.add(ejercicio)

                mostrarEjercicios()

                Toast.makeText(
                    this,
                    "Ejercicio añadido correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutina)

        val nombreRutina = findViewById<EditText>(R.id.etNombreRutina)
        val descripcionRutina = findViewById<EditText>(R.id.etDescripcionRutina)
        val spinnerTipoRutina = findViewById<Spinner>(R.id.spinnerTipoRutina)
        val btnGuardarRutina = findViewById<Button>(R.id.btnGuardarRutina)
        val btnAnadirEjercicio = findViewById<Button>(R.id.btnAnadirEjercicio)

        layoutEjerciciosAnadidos =
            findViewById(R.id.layoutEjerciciosAnadidos)

        val tiposRutina = arrayOf(
            "Pecho",
            "Bíceps",
            "Tríceps",
            "Pierna",
            "Rutina de estiramiento (fisio)"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            tiposRutina
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerTipoRutina.adapter = adapter

        val db = AppDatabase.getDatabase(this)

        btnAnadirEjercicio.setOnClickListener {

            val intent = Intent(
                this,
                EjercicioActivity::class.java
            )

            lanzarEjercicio.launch(intent)
        }

        btnGuardarRutina.setOnClickListener {

            val nombre = nombreRutina.text.toString()
            val descripcion = descripcionRutina.text.toString()
            val tipo = spinnerTipoRutina.selectedItem.toString()

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(
                    this,
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (listaEjercicios.isEmpty()) {
                Toast.makeText(
                    this,
                    "Añade al menos un ejercicio",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val rutina = Rutina(
                nombre = nombre,
                descripcion = descripcion,
                tipoRutina = tipo,
                idUsuario = 1
            )

            lifecycleScope.launch {

                val idRutinaNueva =
                    db.rutinaDao()
                        .insertarRutina(rutina)
                        .toInt()

                for (ejercicio in listaEjercicios) {

                    val ejercicioFinal =
                        ejercicio.copy(
                            idRutina = idRutinaNueva
                        )

                    db.ejercicioDao()
                        .insertarEjercicio(ejercicioFinal)
                }

                Toast.makeText(
                    this@RutinaActivity,
                    "Rutina guardada correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }

    private fun mostrarEjercicios() {

        layoutEjerciciosAnadidos.removeAllViews()

        for (ejercicio in listaEjercicios) {

            val texto = TextView(this)

            texto.text =
                """
                ${ejercicio.nombre}
                Series: ${ejercicio.series}
                Repeticiones: ${ejercicio.repeticiones}
                Duración: ${ejercicio.duracion}
                Dificultad: ${ejercicio.dificultad}
                """.trimIndent()

            texto.textSize = 16f
            texto.setPadding(0, 16, 0, 16)

            layoutEjerciciosAnadidos.addView(texto)
        }
    }
}