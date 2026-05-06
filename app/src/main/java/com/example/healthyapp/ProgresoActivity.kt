package com.example.healthyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import com.example.healthyapp.model.Progreso
import kotlinx.coroutines.launch

class ProgresoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progreso)

        val etPesoCorporal = findViewById<EditText>(R.id.etPesoCorporal)
        val etGrasaCorporal = findViewById<EditText>(R.id.etGrasaCorporal)
        val etPecho = findViewById<EditText>(R.id.etPecho)
        val etCintura = findViewById<EditText>(R.id.etCintura)
        val etCadera = findViewById<EditText>(R.id.etCadera)
        val etBicepsIzq = findViewById<EditText>(R.id.etBicepsIzq)
        val etBicepsDer = findViewById<EditText>(R.id.etBicepsDer)
        val etMusloIzq = findViewById<EditText>(R.id.etMusloIzq)
        val etMusloDer = findViewById<EditText>(R.id.etMusloDer)
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val btnGuardarProgreso = findViewById<Button>(R.id.btnGuardarProgreso)

        val db = AppDatabase.getDatabase(this)

        btnGuardarProgreso.setOnClickListener {
            val pesoTexto = etPesoCorporal.text.toString()
            val fecha = etFecha.text.toString()

            if (pesoTexto.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(this, "Introduce al menos peso y fecha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val progreso = Progreso(
                pesoCorporal = pesoTexto.toDouble(),
                grasaCorporal = etGrasaCorporal.text.toString().toDoubleOrNull() ?: 0.0,
                pecho = etPecho.text.toString().toDoubleOrNull() ?: 0.0,
                cintura = etCintura.text.toString().toDoubleOrNull() ?: 0.0,
                cadera = etCadera.text.toString().toDoubleOrNull() ?: 0.0,
                bicepsIzq = etBicepsIzq.text.toString().toDoubleOrNull() ?: 0.0,
                bicepsDer = etBicepsDer.text.toString().toDoubleOrNull() ?: 0.0,
                musloIzq = etMusloIzq.text.toString().toDoubleOrNull() ?: 0.0,
                musloDer = etMusloDer.text.toString().toDoubleOrNull() ?: 0.0,
                fecha = fecha
            )

            lifecycleScope.launch {
                db.progresoDao().insertarProgreso(progreso)

                Toast.makeText(
                    this@ProgresoActivity,
                    "Progreso guardado correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this@ProgresoActivity, ResultadosActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}