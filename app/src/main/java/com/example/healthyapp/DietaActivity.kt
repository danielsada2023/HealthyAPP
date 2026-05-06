package com.example.healthyapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import com.example.healthyapp.model.Dieta
import kotlinx.coroutines.launch

class DietaActivity : AppCompatActivity() {

    private var archivoUri: Uri? = null
    private lateinit var tvArchivoSeleccionado: TextView

    private val seleccionarArchivo =
        registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                archivoUri = result.data?.data
                tvArchivoSeleccionado.text = "Archivo seleccionado correctamente"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dieta)

        val etNombreDieta = findViewById<EditText>(R.id.etNombreDieta)
        val etDescripcionDieta = findViewById<EditText>(R.id.etDescripcionDieta)

        val btnSeleccionarArchivo = findViewById<Button>(R.id.btnSeleccionarArchivo)
        val btnVerDieta = findViewById<Button>(R.id.btnVerDieta)
        val btnGuardarDieta = findViewById<Button>(R.id.btnGuardarDieta)

        tvArchivoSeleccionado = findViewById(R.id.tvArchivoSeleccionado)

        val db = AppDatabase.getDatabase(this)

        btnSeleccionarArchivo.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "image/*"))
            seleccionarArchivo.launch(intent)
        }

        btnVerDieta.setOnClickListener {
            if (archivoUri != null) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(archivoUri, contentResolver.getType(archivoUri!!))
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona un archivo primero", Toast.LENGTH_SHORT).show()
            }
        }

        btnGuardarDieta.setOnClickListener {
            val nombre = etNombreDieta.text.toString()
            val descripcion = etDescripcionDieta.text.toString()
            val archivo = archivoUri?.toString()

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Introduce nombre y descripción", Toast.LENGTH_SHORT).show()
            } else {
                val dieta = Dieta(
                    nombre = nombre,
                    descripcion = descripcion,
                    archivo = archivo,
                    idUsuario = 1
                )

                lifecycleScope.launch {
                    db.dietaDao().insertarDieta(dieta)

                    Toast.makeText(
                        this@DietaActivity,
                        "Dieta guardada correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}