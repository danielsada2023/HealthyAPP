package com.example.healthyapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class EjercicioActivity : AppCompatActivity() {

    private var imagenUri: Uri? = null
    private var videoUri: Uri? = null

    private lateinit var imgPreview: ImageView
    private lateinit var videoPreview: VideoView

    private val seleccionarImagen =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                imagenUri = result.data?.data

                imgPreview.setImageURI(imagenUri)

                Toast.makeText(
                    this,
                    "Imagen añadida",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val seleccionarVideo =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                videoUri = result.data?.data

                videoPreview.setVideoURI(videoUri)

                videoPreview.start()

                Toast.makeText(
                    this,
                    "Vídeo añadido",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ejercicio)

        val etNombreEjercicio =
            findViewById<EditText>(R.id.etNombreEjercicio)

        val etDescripcionEjercicio =
            findViewById<EditText>(R.id.etDescripcionEjercicio)

        val etRepeticiones =
            findViewById<EditText>(R.id.etRepeticiones)

        val etSeries =
            findViewById<EditText>(R.id.etSeries)

        val etDuracion =
            findViewById<EditText>(R.id.etDuracion)

        val etDificultad =
            findViewById<EditText>(R.id.etDificultad)

        val btnSeleccionarImagen =
            findViewById<Button>(R.id.btnSeleccionarImagenEjercicio)

        val btnSeleccionarVideo =
            findViewById<Button>(R.id.btnSeleccionarVideoEjercicio)

        val btnGuardarEjercicio =
            findViewById<Button>(R.id.btnGuardarEjercicio)

        imgPreview =
            findViewById(R.id.imgEjercicioPreview)

        videoPreview =
            findViewById(R.id.videoEjercicioPreview)

        btnSeleccionarImagen.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT)

            intent.type = "image/*"

            intent.addCategory(Intent.CATEGORY_OPENABLE)

            seleccionarImagen.launch(intent)
        }

        btnSeleccionarVideo.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT)

            intent.type = "video/*"

            intent.addCategory(Intent.CATEGORY_OPENABLE)

            seleccionarVideo.launch(intent)
        }

        btnGuardarEjercicio.setOnClickListener {

            val nombre =
                etNombreEjercicio.text.toString()

            val descripcion =
                etDescripcionEjercicio.text.toString()

            val repeticiones =
                etRepeticiones.text
                    .toString()
                    .toIntOrNull() ?: 0

            val series =
                etSeries.text
                    .toString()
                    .toIntOrNull() ?: 0

            val duracion =
                etDuracion.text
                    .toString()
                    .toIntOrNull() ?: 0

            val dificultad =
                etDificultad.text.toString()

            if (nombre.isEmpty() || descripcion.isEmpty()) {

                Toast.makeText(
                    this,
                    "Introduce nombre y descripción",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val resultado = Intent()

                resultado.putExtra("nombre", nombre)
                resultado.putExtra("descripcion", descripcion)
                resultado.putExtra("repeticiones", repeticiones)
                resultado.putExtra("series", series)
                resultado.putExtra("duracion", duracion)
                resultado.putExtra("dificultad", dificultad)
                resultado.putExtra("imagen", imagenUri?.toString())
                resultado.putExtra("video", videoUri?.toString())

                setResult(Activity.RESULT_OK, resultado)

                finish()
            }
        }
    }
}