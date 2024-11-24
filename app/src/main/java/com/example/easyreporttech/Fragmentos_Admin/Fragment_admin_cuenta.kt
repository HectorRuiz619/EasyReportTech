package com.example.easyreporttech.Fragmentos_Admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.easyreporttech.Elegir_rol
import com.example.easyreporttech.R
import com.example.easyreporttech.databinding.FragmentAdminCuentaBinding
import com.google.firebase.auth.FirebaseAuth

class Fragment_admin_cuenta : Fragment() {

    private var _binding: FragmentAdminCuentaBinding? = null
    private val binding get() = _binding!! // Asegura que el binding no sea nulo mientras el fragmento esté activo
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Usamos el inflater proporcionado en los argumentos
        _binding = FragmentAdminCuentaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configurar el botón para cerrar sesión
        binding.CerrarSesionAdmin.setOnClickListener {
            try {
                firebaseAuth.signOut()
                // Iniciar la actividad Elegir_rol
                startActivity(Intent(requireContext(), Elegir_rol::class.java))
                activity?.finishAffinity() // Finalizar todas las actividades
            } catch (e: Exception) {
                // Manejar cualquier posible excepción al cerrar sesión
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Liberar el binding para prevenir fugas de memoria
    }
}
