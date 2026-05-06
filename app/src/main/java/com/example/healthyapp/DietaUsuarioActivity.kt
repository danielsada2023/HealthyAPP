package com.example.healthyapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import kotlinx.coroutines.launch

class DietaUsuarioActivity : AppCompatActivity() {

    private var archivoUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dieta_usuario)

        val tvNombreDieta = findViewById<TextView>(R.id.tvNombreDietaUsuario)
        val tvDescripcionDieta = findViewById<TextView>(R.id.tvDescripcionDietaUsuario)
        val tvArchivoDieta = findViewById<TextView>(R.id.tvArchivoDietaUsuario)
        val btnVerDieta = findViewById<Button>(R.id.btnVerDietaUsuario)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val dieta = db.dietaDao().obtenerDietaUsuario(1)

            if (dieta != null) {
                tvNombreDieta.text = "Nombre: ${dieta.nombre}"
                tvDescripcionDieta.text = "Descripción: ${dieta.descripcion}"
                tvArchivoDieta.text = "Archivo: dieta asignada"
                archivoUri = dieta.archivo
            } else {
                tvNombreDieta.text = "No hay dieta asignada"
                tvDescripcionDieta.text = ""
                tvArchivoDieta.text = ""
            }
        }

        btnVerDieta.setOnClickListener {
            if (archivoUri != null) {
                val uri = Uri.parse(archivoUri)

                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, contentResolver.getType(uri))
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay archivo de dieta disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }
}