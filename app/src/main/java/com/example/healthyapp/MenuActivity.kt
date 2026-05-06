package com.example.healthyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val tvTipoUsuario = findViewById<TextView>(R.id.tvTipoUsuario)

        val btnRutina = findViewById<Button>(R.id.btnRutina)
        val btnMisRutinas = findViewById<Button>(R.id.btnMisRutinas)
        val btnDieta = findViewById<Button>(R.id.btnDieta)
        val btnVerDieta = findViewById<Button>(R.id.btnVerDieta)
        val btnProgreso = findViewById<Button>(R.id.btnProgreso)
        val btnResultados = findViewById<Button>(R.id.btnResultados)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        val tipoUsuario = intent.getStringExtra("tipoUsuario")

        tvTipoUsuario.text = when (tipoUsuario) {
            "usuario" -> "Perfil: Usuario"
            "entrenador" -> "Perfil: Entrenador"
            "mantenimiento" -> "Perfil: Mantenimiento"
            else -> "Perfil: Desconocido"
        }

        btnRutina.visibility = View.GONE
        btnMisRutinas.visibility = View.GONE
        btnDieta.visibility = View.GONE
        btnVerDieta.visibility = View.GONE
        btnProgreso.visibility = View.GONE
        btnResultados.visibility = View.GONE

        when (tipoUsuario) {
            "usuario" -> {
                btnMisRutinas.visibility = View.VISIBLE
                btnVerDieta.visibility = View.VISIBLE
                btnProgreso.visibility = View.VISIBLE
            }

            "entrenador" -> {
                btnRutina.visibility = View.VISIBLE
                btnDieta.visibility = View.VISIBLE
                btnResultados.visibility = View.VISIBLE
            }

            "mantenimiento" -> {
                btnRutina.visibility = View.VISIBLE
                btnMisRutinas.visibility = View.VISIBLE
                btnDieta.visibility = View.VISIBLE
                btnVerDieta.visibility = View.VISIBLE
                btnProgreso.visibility = View.VISIBLE
                btnResultados.visibility = View.VISIBLE
            }
        }

        btnCerrarSesion.visibility = View.VISIBLE

        btnRutina.setOnClickListener {
            startActivity(Intent(this, RutinaActivity::class.java))
        }

        btnMisRutinas.setOnClickListener {
            startActivity(Intent(this, RutinasUsuarioActivity::class.java))
        }

        btnDieta.setOnClickListener {
            startActivity(Intent(this, DietaActivity::class.java))
        }

        btnVerDieta.setOnClickListener {
            startActivity(Intent(this, DietaUsuarioActivity::class.java))
        }

        btnProgreso.setOnClickListener {
            startActivity(Intent(this, ProgresoActivity::class.java))
        }

        btnResultados.setOnClickListener {
            startActivity(Intent(this, ResultadosActivity::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}