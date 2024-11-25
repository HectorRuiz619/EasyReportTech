package com.example.easyreporttech.Administrador

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.easyreporttech.MainActivity
import com.example.easyreporttech.R
import com.example.easyreporttech.databinding.ActivityLoginAdminBinding
import com.google.firebase.auth.FirebaseAuth

class login_Admin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de FirebaseAuth y ProgressDialog
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this).apply {
            setTitle("Espere por favor")
            setCanceledOnTouchOutside(false)
        }

        // Configuración del botón "Regresar"
        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Configuración del botón "Iniciar Sesión"
        binding.BtnLoginAdmin.setOnClickListener {
            validarInformacion()
        }
    }

    private fun validarInformacion() {
        email = binding.EtEmailAdmin.text.toString().trim()
        password = binding.EtPasswordAdmin.text.toString().trim()

        when {
            email.isEmpty() -> {
                binding.EtEmailAdmin.error = "Ingrese su Correo"
                binding.EtEmailAdmin.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.EtEmailAdmin.error = "Correo invalido"
                binding.EtEmailAdmin.requestFocus()
            }
            password.isEmpty() -> {
                binding.EtPasswordAdmin.error = "Ingrese la contraseña"
                binding.EtPasswordAdmin.requestFocus()
            }
            else -> LoginAdmin()
        }
    }

    private fun LoginAdmin() {
        progressDialog.setMessage("Iniciando Sesion")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@login_Admin, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se puedo iniciar sesion debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
