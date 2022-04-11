package com.example.firebaselogin.fragments

import android.app.ProgressDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.firebaselogin.R
import com.example.firebaselogin.base.BaseFragment
import com.example.firebaselogin.databinding.PerfilFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.firebaselogin.Login

class PerfilFragment : BaseFragment<PerfilFragmentBinding>(PerfilFragmentBinding::inflate) {
    private lateinit var firebaseAuth: FirebaseAuth
    private var db = FirebaseFirestore.getInstance()
    private var timer = Timer()
    private var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.btCerrarSesion.setOnClickListener{cerrarSesion()}
        binding.btEditarPerfil.setOnClickListener{ editarPerfil() }
        binding.btGuardarCambios.setOnClickListener { guardarPerfil() }
        binding.btEliminarCuenta.setOnClickListener { eliminarCuenta() }
        binding.btCambiarPwd.setOnClickListener {
            findNavController(this).navigate(R.id.action_navPerfil_to_navCambiarPwd)
        }
    }

    private fun eliminarCuenta() {
        val builder = super.getContext()?.let {AlertDialog.Builder (it)}
            builder?.setTitle("Eliminar cuenta")
            builder?.setMessage("Al eliminar tu cuenta se borrarán tus datos de forma permanente. Deseas continuar?")
            builder?.setIcon(R.drawable.ic_warning)
            builder?.setPositiveButton("Eliminar"){
                    dialog,which ->
                val usuario = firebaseAuth.currentUser
                val progress = ProgressDialog(super.getContext())
                progress.setTitle("Eliminando cuenta")
                progress.setMessage("Procesando...")
                progress.setCanceledOnTouchOutside(false)
                progress.show()
                timer.schedule(object : TimerTask(){
                    override fun run() {
                        count ++
                        progress.setProgress(count)
                        if (count > 50){
                            timer.cancel()
                            usuario?.delete()?.addOnSuccessListener {
                                db.collection("usuarios").document(firebaseAuth.currentUser?.email.toString()).delete()
                            Toast.makeText(context, "Cuenta eliminada con éxito", Toast.LENGTH_LONG).show()
                            view?.let {
                                Navigation.findNavController(it).navigate(R.id.action_navPerfil_to_navLogin)
                            }
                            }
                                ?.addOnFailureListener {
                                    Toast.makeText(context, "No se pudo eliminar la cuenta", Toast.LENGTH_LONG).show()
                                }
                            progress.dismiss()
                        }
                    }
                }, 0,50)
            }
            builder?.setNegativeButton("Cancelar"){
                    dialog, which ->
                Toast.makeText(context, "Operación cancelada", Toast.LENGTH_SHORT).show()
            }
        val dialog : AlertDialog = builder!!.create()
        dialog.show()
    }

    private fun guardarPerfil() {
        guardarCambiosPerfil()
        deshabilitarItems()
    }

    private fun deshabilitarItems() {
        binding.etNombrePerfil.isEnabled=false
        binding.etApellidoPerfil.isEnabled=false
        binding.btGuardarCambios.isEnabled=false
        binding.btEditarPerfil.isEnabled=true
    }

    private fun guardarCambiosPerfil() {
        var mail = binding.tvEmailUsr.text.toString()
        val name = binding.etNombrePerfil.text.toString()
        val apellido = binding.etApellidoPerfil.text.toString()
        db.collection("usuarios").document(mail).set(
            hashMapOf(
                "nombre" to name,
                "apellido" to apellido,
            )
        )
        Toast.makeText(super.getContext(),"Cambios guardados", Toast.LENGTH_LONG).show()
    }

    private fun editarPerfil() {
        binding.etNombrePerfil.isEnabled=true
        binding.etApellidoPerfil.isEnabled=true
        binding.btGuardarCambios.isEnabled=true
        binding.btEditarPerfil.isEnabled=false
    }

    private fun cerrarSesion() {
        firebaseAuth.signOut()
        checkUser()
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null) {
            val mail = firebaseUser.email
            binding.tvEmailUsr.text = mail
            db.collection("usuarios").document(mail.toString()).get().addOnSuccessListener {
                binding.etNombrePerfil.setText(it.get("nombre") as String?)
                binding.etApellidoPerfil.setText(it.get("apellido") as String?)
            }
        }else { //falta completar
        }
    }
}