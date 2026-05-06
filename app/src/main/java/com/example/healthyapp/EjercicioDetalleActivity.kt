package com.example.healthyapp

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EjercicioDetalleActivity : AppCompatActivity() {

    private var videoUri: Uri? = null
    private lateinit var videoEjercicio: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio_detalle)

        val tvNombre = findViewById<TextView>(R.id.tvNombreEjercicio)
        val tvRepeticiones = findViewById<TextView>(R.id.tvRepeticiones)
        val tvSeries = findViewById<TextView>(R.id.tvSeries)
        val tvDuracion = findViewById<TextView>(R.id.tvDuracion)
        val tvDificultad = findViewById<TextView>(R.id.tvDificultad)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcionEjercicio)
        val imgEjercicio = findViewById<ImageView>(R.id.imgEjercicioDetalle)
        videoEjercicio = findViewById(R.id.videoEjercicioDetalle)
        val btnReproducirVideo = findViewById<Button>(R.id.btnReproducirVideoDetalle)
        val btnCompletado = findViewById<Button>(R.id.btnCompletado)

        val db = AppDatabase.getDatabase(this)
        val idEjercicio = intent.getIntExtra("idEjercicio", 1)

        lifecycleScope.launch {
            val ejercicio = db.ejercicioDao().obtenerEjercicioPorId(idEjercicio)

            if (ejercicio != null) {
                tvNombre.text = "Nombre: ${ejercicio.nombre}"
                tvRepeticiones.text = "Repeticiones: ${ejercicio.repeticiones}"
                tvSeries.text = "Series: ${ejercicio.series}"
                tvDuracion.text = "Duración: ${ejercicio.duracion} minutos"
                tvDificultad.text = "Dificultad: ${ejercicio.dificultad}"
                tvDescripcion.text = "Descripción: ${ejercicio.descripcion}"

                if (!ejercicio.imagen.isNullOrEmpty()) {
                    imgEjercicio.setImageURI(Uri.parse(ejercicio.imagen))
                }

                if (!ejercicio.video.isNullOrEmpty()) {
                    videoUri = Uri.parse(ejercicio.video)
                    videoEjercicio.setVideoURI(videoUri)
                }

                if (ejercicio.completado) {
                    btnCompletado.text = "Completado el ${ejercicio.fechaCompletado}"
                }
            } else {
                tvNombre.text = "Ejercicio no encontrado"
            }
        }

        btnReproducirVideo.setOnClickListener {
            if (videoUri != null) {
                videoEjercicio.start()
            } else {
                Toast.makeText(this, "No hay vídeo disponible", Toast.LENGTH_SHORT).show()
            }
        }

        btnCompletado.setOnClickListener {
            val fechaActual = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(Date())

            lifecycleScope.launch {
                db.ejercicioDao().marcarEjercicioCompletado(
                    idEjercicio,
                    fechaActual
                )

                btnCompletado.text = "Completado el $fechaActual"

                Toast.makeText(
                    this@EjercicioDetalleActivity,
                    "Ejercicio completado el $fechaActual",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}