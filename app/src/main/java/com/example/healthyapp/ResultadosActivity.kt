package com.example.healthyapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.healthyapp.database.AppDatabase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch

class ResultadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        val tvPesoResultado = findViewById<TextView>(R.id.tvPesoResultado)
        val tvFechaResultado = findViewById<TextView>(R.id.tvFechaResultado)
        val listResultados = findViewById<ListView>(R.id.listResultados)
        val listEjerciciosCompletados = findViewById<ListView>(R.id.listEjerciciosCompletados)
        val chartPeso = findViewById<LineChart>(R.id.chartPeso)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val progresos = db.progresoDao().obtenerProgresos()

            if (progresos.isEmpty()) {
                tvPesoResultado.text = "Peso corporal: - kg"
                tvFechaResultado.text = "Fecha: -"

                listResultados.adapter = ArrayAdapter(
                    this@ResultadosActivity,
                    android.R.layout.simple_list_item_1,
                    listOf("No hay progresos registrados")
                )

                chartPeso.clear()
            } else {
                val ultimo = progresos.first()

                tvPesoResultado.text = "Peso corporal: ${ultimo.pesoCorporal} kg"
                tvFechaResultado.text = "Fecha: ${ultimo.fecha}"

                val listaTexto = progresos.map {
                    """
                    Fecha: ${it.fecha}
                    Peso: ${it.pesoCorporal} kg
                    Grasa: ${it.grasaCorporal} %
                    Pecho: ${it.pecho} cm
                    Cintura: ${it.cintura} cm
                    Cadera: ${it.cadera} cm
                    Bíceps izq: ${it.bicepsIzq} cm
                    Bíceps der: ${it.bicepsDer} cm
                    Muslo izq: ${it.musloIzq} cm
                    Muslo der: ${it.musloDer} cm
                    """.trimIndent()
                }

                listResultados.adapter = ArrayAdapter(
                    this@ResultadosActivity,
                    android.R.layout.simple_list_item_1,
                    listaTexto
                )

                crearGrafica(chartPeso, progresos.reversed())
            }

            val ejerciciosCompletados = db.ejercicioDao().obtenerEjerciciosCompletados()

            val listaEjercicios =
                if (ejerciciosCompletados.isEmpty()) {
                    listOf("No hay ejercicios completados")
                } else {
                    ejerciciosCompletados.map {
                        "${it.nombre} - Completado: ${it.fechaCompletado ?: "sin fecha"}"
                    }
                }

            listEjerciciosCompletados.adapter = ArrayAdapter(
                this@ResultadosActivity,
                android.R.layout.simple_list_item_1,
                listaEjercicios
            )
        }
    }

    private fun crearGrafica(chart: LineChart, progresos: List<com.example.healthyapp.model.Progreso>) {
        val entradas = ArrayList<Entry>()

        progresos.forEachIndexed { index, progreso ->
            entradas.add(
                Entry(
                    (index + 1).toFloat(),
                    progreso.pesoCorporal.toFloat()
                )
            )
        }

        val dataSet = LineDataSet(entradas, "Evolución del peso")
        val lineData = LineData(dataSet)

        chart.data = lineData
        chart.invalidate()
    }
}