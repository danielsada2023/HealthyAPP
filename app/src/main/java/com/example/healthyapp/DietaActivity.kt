package com.example.healthyapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import com.example.healthyapp.model.Dieta
import kotlinx.coroutines.launch

class DietaActivity : AppCompatActivity() {

    private var archivoUri: Uri? = null

    private val seleccionarArchivo =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                archivoUri = result.data?.data

                archivoUri?.let { uri ->

                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }

                findViewById<TextView>(R.id.tvArchivoSeleccionado).text =
                    "Archivo seleccionado correctamente"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dieta)

        val etNombre =
            findViewById<EditText>(R.id.etNombreDieta)

        val etDescripcion =
            findViewById<EditText>(R.id.etDescripcionDieta)

        val btnSeleccionar =
            findViewById<Button>(R.id.btnSeleccionarArchivo)

        val btnGuardar =
            findViewById<Button>(R.id.btnGuardarDieta)

        val btnVer =
            findViewById<Button>(R.id.btnVerDieta)

        val db = AppDatabase.getDatabase(this)

        btnSeleccionar.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT)

            intent.type = "*/*"

            intent.addCategory(
                Intent.CATEGORY_OPENABLE
            )

            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            intent.addFlags(
                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            )

            intent.putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf(
                    "application/pdf",
                    "image/*"
                )
            )

            seleccionarArchivo.launch(intent)
        }

        btnVer.setOnClickListener {

            if (archivoUri != null) {

                val intent =
                    Intent(Intent.ACTION_VIEW)

                intent.setDataAndType(
                    archivoUri,
                    contentResolver.getType(archivoUri!!)
                )

                intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                startActivity(intent)

            } else {

                Toast.makeText(
                    this,
                    "No hay archivo seleccionado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnGuardar.setOnClickListener {

            val nombre =
                etNombre.text.toString()

            val descripcion =
                etDescripcion.text.toString()

            if (nombre.isEmpty() ||
                descripcion.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val dieta = Dieta(
                nombre = nombre,
                descripcion = descripcion,
                archivo = archivoUri?.toString(),
                idUsuario = 1
            )

            lifecycleScope.launch {

                db.dietaDao()
                    .insertarDieta(dieta)

                Toast.makeText(
                    this@DietaActivity,
                    "Dieta guardada correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}