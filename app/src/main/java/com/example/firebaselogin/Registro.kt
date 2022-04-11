package com.example.firebaselogin
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.firebaselogin.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
class Registro : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var db = FirebaseFirestore.getInstance()
    private var email = ""
    private var contraseña = ""
    private var confirmarContraseña = ""
    private var nombre = ""
    private var apellido = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.title = "Registro"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor, espere...")
        progressDialog.setMessage("Registrando...")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btRegistrarse.setOnClickListener{
            validacionUser()
        }
    }
    private fun validacionUser() {
        email = binding.etEmail.text.toString().trim()
        contraseña = binding.etPwd.text.toString().trim()
        confirmarContraseña = binding.etPwdConfirmar.text.toString().trim()
        nombre = binding.etNombre.text.toString().trim()
        apellido = binding.etApellido.text.toString().trim()
      when{
          TextUtils.isEmpty(contraseña) ->  binding.etPwd.setError("No puede ser vacía")
          TextUtils.isEmpty(nombre) -> binding.etNombre.setError("Completar nombre")
          TextUtils.isEmpty(apellido) -> binding.etApellido.setError("Completar apellido")
          !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> binding.etEmail.setError("Email inválido")
          nombre.contains(Regex("[^a-zA-Z '-]")) ->  binding.etNombre.setError("Solo puede contener letras, espacios, \"-\" y \"'\"")
          apellido.contains(Regex("[^a-zA-Z '-]")) -> binding.etApellido.setError("Solo puede contener letras, espacios, \"-\" y \"'\"")
          !contraseña.contains(Regex("[^a-zA-Z0-9 ]")) -> binding.etPwd.setError("Debe contener al menos un número")
          !contraseña.contains(Regex("[^a-zA-Z0-9 ]")) -> binding.etPwd.setError("Debe contener al menos un caracter especial")
          !contraseña.contains(Regex("[A-Z]")) -> binding.etPwd.setError("Debe contener al menos una mayúscula")
          contraseña.length < 6 -> binding.etPwd.setError("Debe ser mayor a 6 caracteres")
          contraseña != confirmarContraseña -> binding.etPwdConfirmar.setError("Las contraseñas no coinciden")
          else -> firebaseRegister()
        }
    }
    private fun firebaseRegister() {
        progressDialog.show()
        databaseRegister()
        firebaseAuth.createUserWithEmailAndPassword(email, contraseña)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val mail = firebaseUser!!.email
                Toast.makeText(this, "$mail registrado con éxito", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, Login::class.java))
            }
            .addOnFailureListener {
                e -> progressDialog.dismiss()
                Toast.makeText(this, "Error al crear cuenta", Toast.LENGTH_LONG).show()
            }
    }
    private fun databaseRegister() {
        val email = binding.etEmail.text.toString()
        val nombre = binding.etNombre.text.toString()
        val apellido = binding.etApellido.text.toString()
        db.collection("usuarios").document(email).set(
            hashMapOf(
                "nombre" to nombre,
                "apellido" to apellido,
            )
        )
        Toast.makeText(this,"Registro exitoso", Toast.LENGTH_LONG).show()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}