package com.example.healthyapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import kotlinx.coroutines.launch

class RutinasUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutinas_usuario)

        val listRutinasUsuario = findViewById<ListView>(R.id.listRutinasUsuario)
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val rutinas = db.rutinaDao().obtenerRutinasUsuario(1)

            val listaTexto = if (rutinas.isEmpty()) {
                listOf("No hay rutinas guardadas")
            } else {
                rutinas.map { rutina ->
                    "${rutina.nombre}\nTipo: ${rutina.tipoRutina}\n${rutina.descripcion}"
                }
            }

            val adapter = ArrayAdapter(
                this@RutinasUsuarioActivity,
                android.R.layout.simple_list_item_1,
                listaTexto
            )

            listRutinasUsuario.adapter = adapter

            listRutinasUsuario.setOnItemClickListener { _, _, position, _ ->
                if (rutinas.isNotEmpty()) {
                    val rutinaSeleccionada = rutinas[position]

                    val intent = Intent(
                        this@RutinasUsuarioActivity,
                        EjerciciosUsuarioActivity::class.java
                    )

                    intent.putExtra("idRutina", rutinaSeleccionada.idRutina)
                    startActivity(intent)
                }
            }
        }
    }
}