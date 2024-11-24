package com.example.easyreporttech

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Welcome : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        firebaseAuth = FirebaseAuth.getInstance()
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

                ComprobarSesion()
            }
        }.start()
    }

    /**
     * Verifica si hay un usuario autenticado y redirige según su rol.
     * - Si no hay usuario, redirige a `Elegir_rol`.
     * - Si el usuario es "admin", redirige a `MainActivity`.
     */
    fun ComprobarSesion() {
        // Obtiene el usuario actual autenticado en Firebase
        val firebaseUser = firebaseAuth.currentUser

        // Verifica si no hay un usuario autenticado
        if (firebaseUser == null) {
            // Redirige a la pantalla de selección de rol si no hay usuario autenticado
            startActivity(Intent(this, Elegir_rol::class.java))
            // Finaliza todas las actividades actuales para evitar volver atrás
            finishAffinity()
            return
        }

        // Referencia a la rama "Usuarios" en Firebase Realtime Database
        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")

        // Busca los datos del nodo correspondiente al UID del usuario autenticado
        reference.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                // Se ejecuta cuando se obtienen los datos de Firebase
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Obtiene el valor del campo "rol" del usuario
                    val rol = snapshot.child("rol").value
                    // Verifica si el rol del usuario es "admin"
                    if (rol == "admin") {
                        // Redirige al usuario a la pantalla principal de administrador
                        startActivity(Intent(this@Welcome, MainActivity::class.java))
                        // Finaliza todas las actividades actuales
                        finishAffinity()
                    }
                }

                // Se ejecuta si ocurre un error al obtener los datos de Firebase
                override fun onCancelled(error: DatabaseError) {
                    // Opcional: Manejo de errores al acceder a la base de datos
                }
            })
    }

}
