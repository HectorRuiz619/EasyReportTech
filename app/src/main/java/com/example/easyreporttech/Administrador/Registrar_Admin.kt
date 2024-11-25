package com.example.easyreporttech.Administrador

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.easyreporttech.MainActivity
import com.example.easyreporttech.R
import com.example.easyreporttech.databinding.ActivityRegistrarAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.intellij.lang.annotations.Pattern

class Registrar_Admin : AppCompatActivity() {


private lateinit var  binding : ActivityRegistrarAdminBinding

private lateinit var firebaseAuth : FirebaseAuth
private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)






binding.IbRegresar.setOnClickListener {
    onBackPressedDispatcher.onBackPressed()
}
        binding.BtnRegistrarAdmin.setOnClickListener {
            validarInformacion()
        }
binding.TxtTengoCuenta.setOnClickListener {
    startActivity(Intent(this@Registrar_Admin, login_Admin::class.java))
}

     //   setContentView(R.layout.activity_registrar_admin)
    }

    var nombres = ""
    var email = ""
    var password = ""
    var r_password = ""


    private fun validarInformacion() {
        nombres = binding.EtNombresAdmin.text.toString().trim()
        email = binding.EtEmailAdmin.text.toString().trim()
        password = binding.EtPasswordAdmin.text.toString().trim()
        r_password = binding.EtRPasswordAdmin.text.toString().trim()

        if (nombres.isEmpty()) {
            binding.EtNombresAdmin.error = "Ingrese nombres"
            binding.EtNombresAdmin.requestFocus()

        } else if (email.isEmpty()) {
            binding.EtEmailAdmin.error = "Ingrese Email"
            binding.EtEmailAdmin.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtEmailAdmin.error = "Email no es Valido"
            binding.EtEmailAdmin.requestFocus()

        }
        else if (password.isEmpty()){
            binding.EtPasswordAdmin.error = "ingresa la Contraseña"
            binding.EtPasswordAdmin.requestFocus()

        }
        else if (password.length <6){
            binding.EtPasswordAdmin.error = "La Contraseña debe tener mas de 6 caracteres"
            binding.EtPasswordAdmin.requestFocus()
        }
        else if (r_password.isEmpty()){
            binding.EtRPasswordAdmin.error = "Repita la Contraseña"
            binding.EtRPasswordAdmin.requestFocus()

        }
        else if (password != r_password){

            binding.EtRPasswordAdmin.error = "Las Contraseñas no Coinsiden"
            binding.EtRPasswordAdmin.requestFocus()
        }
        else{
            CrearCuentaAdmin(email, password)
        }




    }

    private fun CrearCuentaAdmin(email: String, password: String) {
        progressDialog.setMessage("Creando Cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                AgregarInfoBD()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Ha fallado la creacion de la cuenta debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun AgregarInfoBD() {
        progressDialog.setMessage("Guardando informacion ... ")
        val tiempo = System.currentTimeMillis()
        val uid = firebaseAuth.uid

        val datos_admin : HashMap<String, Any?> = HashMap()
        datos_admin["uid"] = uid
        datos_admin["nombres"] = nombres
        datos_admin["email"] = email
        datos_admin["rol"] = "admin"
        datos_admin["tiempo_registro"] = tiempo
        datos_admin["imagen"] = ""


        val  reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uid!!)
            .setValue(datos_admin)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Cuenta Creada", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()


            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "no se puedo Gurardar la informacion debido a ${e.message}", Toast.LENGTH_SHORT).show()


            }


    }
}
