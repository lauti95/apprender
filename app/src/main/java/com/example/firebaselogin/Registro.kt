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
    private var repetirContraseña = ""
    private var name = ""
    private var apellido = ""
    private var curso = ""
    private var materia = ""
    private var escuela = ""
    private var rol = ""
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
        val spinner: Spinner = binding.spRollRegister
        ArrayAdapter.createFromResource(this, R.array.lista_roles, android.R.layout.simple_spinner_item)
            .also{ adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
        }
        binding.btnRegister.setOnClickListener{
            validacionUser()
        }
    }

    private fun validacionUser() {
        email = binding.etEmailRegister.text.toString().trim()
        contraseña = binding.etPwdRegister.text.toString().trim()
        repetirContraseña = binding.etPwdConfirm.text.toString().trim()
        name = binding.etNameRegister.text.toString().trim()
        apellido = binding.etSurnameRegister.text.toString().trim()
        escuela = binding.etSchoolRegister.text.toString().trim()
        curso = binding.etClassRegister.text.toString().trim()
        materia = binding.etSubjectRegister.text.toString().trim()
        rol = binding.spRollRegister.selectedItem.toString().trim()
      when{
          TextUtils.isEmpty(contraseña) ->  binding.etPwdConfirm.setError("No puede ser vacía")
          TextUtils.isEmpty(name) -> binding.etNameRegister.setError("Completar nombre")
          TextUtils.isEmpty(apellido) -> binding.etSurnameRegister.setError("Completar apellido")
          !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> binding.etEmailRegister.setError("Email inválido")
          TextUtils.isEmpty(curso) -> binding.etClassRegister.setError("Completar curso")
          TextUtils.isEmpty(materia) -> binding.etSubjectRegister.setError("Completar materia")
          TextUtils.isEmpty(escuela) -> binding.etSchoolRegister.setError("Completar escuela")
          name.contains(Regex("[^a-zA-Z '-]")) ->  binding.etNameRegister.setError("Solo puede contener letras, espacios, \"-\" y \"'\"")
          apellido.contains(Regex("[^a-zA-Z '-]")) -> binding.etSurnameRegister.setError("Solo puede contener letras, espacios, \"-\" y \"'\"")
          !contraseña.contains(Regex("[^a-zA-Z0-9 ]")) -> binding.etPwdRegister.setError("Debe contener al menos un número")
          !contraseña.contains(Regex("[^a-zA-Z0-9 ]")) -> binding.etPwdRegister.setError("Debe contener al menos un caracter especial")
          !contraseña.contains(Regex("[A-Z]")) -> binding.etPwdRegister.setError("Debe contener al menos una mayúscula")
          Regex("^[1-9][A-Z]$") !in curso -> binding.etClassRegister.setError("Debe contener 1 número y 1 letra mayúscula, en ese orden")
          contraseña.length < 6 -> binding.etPwdRegister.setError("Debe ser mayor a 6 caracteres")
          contraseña != repetirContraseña -> binding.etPwdConfirm.setError("Las contraseñas no coinciden")
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
        val email = binding.etEmailRegister.text.toString()
        val nombre = binding.etNameRegister.text.toString()
        val apellido = binding.etSurnameRegister.text.toString()
        val rol = binding.spRollRegister.selectedItem.toString()
        val escuela = binding.etSchoolRegister.text.toString()
        val materia = binding.etSubjectRegister.text.toString()
        val curso = binding.etClassRegister.text.toString()
        db.collection("usuarios").document(email).set(
            hashMapOf(
                "nombre" to nombre,
                "apellido" to apellido,
                "rol" to rol,
                "escuela" to escuela,
                "materia" to materia,
                "curso" to curso,
            )
        )
        Toast.makeText(this,"Registro exitoso", Toast.LENGTH_LONG).show()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}