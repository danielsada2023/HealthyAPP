package com.example.healthyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val btnEntrenador = findViewById<Button>(R.id.btnEntrenador)
        val btnUsuario = findViewById<Button>(R.id.btnUsuario)

        btnEntrenador.setOnClickListener {
            iniciarSesion(
                email.text.toString(),
                password.text.toString(),
                "entrenador"
            )
        }

        btnUsuario.setOnClickListener {
            iniciarSesion(
                email.text.toString(),
                password.text.toString(),
                "usuario"
            )
        }
    }

    private fun iniciarSesion(
        email: String,
        password: String,
        tipoElegido: String
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                "Rellena todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        var tipoUsuario: String? = null

        if (email == "admin" && password == "1234") {
            tipoUsuario = "mantenimiento"
        } else if (
            email == "entrenador" &&
            password == "1234" &&
            tipoElegido == "entrenador"
        ) {
            tipoUsuario = "entrenador"
        } else if (
            email == "usuario" &&
            password == "1234" &&
            tipoElegido == "usuario"
        ) {
            tipoUsuario = "usuario"
        }

        if (tipoUsuario == null) {
            Toast.makeText(
                this,
                "Usuario o contraseña incorrectos",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("tipoUsuario", tipoUsuario)
            startActivity(intent)
            finish()
        }
    }
}