package com.example.easyreporttech

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easyreporttech.Fragmentos_Admin.Fragment_admin_cuenta
import com.example.easyreporttech.Fragmentos_Admin.Fragment_admin_dashboard
import com.example.easyreporttech.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el layout con ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar fragmento inicial (Dashboard)
        verFragmentoDashboard()

        // Configurar el BottomNavigationView con los elementos del menú
        binding.BottomNvAdmin.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Menu_panel -> {
                    verFragmentoDashboard()
                    true
                }
                R.id.Menu_cuenta -> {
                    verFragmentoCuenta()
                    true
                }
                else -> false
            }
        }

        // Configurar la barra de herramientas para adaptarse a las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarRLAmin) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun verFragmentoDashboard() {
        val nombreTitulo = "Dashboard"
        binding.TituloRLAdmin.text = nombreTitulo

        val fragment = Fragment_admin_dashboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id, fragment, "Fragment dashboard")
        fragmentTransaction.commit()
    }

    private fun verFragmentoCuenta() {
        val nombreTitulo = "Mi cuenta"
        binding.TituloRLAdmin.text = nombreTitulo

        val fragment = Fragment_admin_cuenta()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id, fragment, "Fragment Mi Cuenta")
        fragmentTransaction.commit()
    }
}
