package com.example.easyreporttech

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Llama a la función que muestra la pantalla de bienvenida
        VerBienvenida()
    }

    // Mueve esta función dentro de la clase Welcome
    private fun VerBienvenida() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
                // Aquí puedes poner algo si necesitas hacer algo en cada tick
            }

            override fun onFinish() {
                // Cuando el temporizador termina, pasa a la MainActivity
                val intent = Intent(this@Welcome, MainActivity::class.java)
                startActivity(intent)
                finishAffinity() // Cierra todas las actividades anteriores
            }
        }.start()
    }
}
