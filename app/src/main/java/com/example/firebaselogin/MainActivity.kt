package com.example.firebaselogin
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebaselogin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var firebaseAuth : FirebaseAuth
    private var db = FirebaseFirestore.getInstance()
    private var timer = Timer()
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.btnLogOutProfile.setOnClickListener{logOut()}
        binding.btnEditProfile.setOnClickListener{ editProfile() }
        binding.btnSaveProfile.setOnClickListener { saveProfile() }
        binding.btnDeleteProfile.setOnClickListener { deleteProfile() }
        binding.btnPwdChangeProfile.setOnClickListener { val intent = Intent(this, ChangePwdActivity::class.java)
                                                            startActivity(intent)}
    }
    private fun deleteProfile() {
        val builder = AlertDialog.Builder(this).setTitle("Eliminar cuenta")
            .setMessage("Al eliminar tu cuenta se borrarán tus datos de forma permanente. Deseas continuar?")
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton("Eliminar"){
                dialog,which ->
                db.collection("usuarios").document(firebaseAuth.currentUser?.email.toString()).delete()
                val usuario = firebaseAuth.currentUser
                usuario?.delete()
                val progress = ProgressDialog(this)
                progress.setTitle("Eliminando cuenta")
                progress.setMessage("Procesando...")
                progress.setCanceledOnTouchOutside(false)
                progress.show()
                val intent = Intent(this, Login::class.java)
                timer.schedule(object : TimerTask(){
                    override fun run() {
                        count ++
                        progress.setProgress(count)
                        if (count > 50){
                            timer.cancel()
                            progress.setMessage("Cuenta eliminada con éxito")
                            progress.dismiss()
                            startActivity(intent)
                            finish()
                        }
                    }
                }, 0,50)
            }
            .setNegativeButton("Cancelar"){
                dialog, which ->
                Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
            }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }
    private fun saveProfile() {
        saveProfileChanges()
        disableItems()
    }
    private fun saveProfileChanges() {
        var mail = binding.tvEmailProfile.text.toString()
        val name = binding.etNameProfile.text.toString()
        val apellido = binding.etSurnameProfile.text.toString()
        val escuela = binding.etSchoolProfile.text.toString()
        val materia = binding.etSubjectProfile.text.toString()
        val curso = binding.etClassProfile.text.toString()
        db.collection("usuarios").document(mail).set(
            hashMapOf(
                "nombre" to name,
                "apellido" to apellido,
                "escuela" to escuela,
                "materia" to materia,
                "curso" to curso,
            )
        )
        Toast.makeText(this,"Cambios guardados", Toast.LENGTH_LONG).show()
    }
    private fun editProfile() {
        enableItems()
    }
    private fun enableItems() {
        binding.etNameProfile.isEnabled=true
        binding.etSurnameProfile.isEnabled=true
        binding.etClassProfile.isEnabled=true
        binding.etSubjectProfile.isEnabled=true
        binding.etSchoolProfile.isEnabled=true
        binding.btnSaveProfile.isEnabled=true
        binding.btnEditProfile.isEnabled=false
    }
    private fun disableItems(){
        binding.etNameProfile.isEnabled=false
        binding.etSurnameProfile.isEnabled=false
        binding.etClassProfile.isEnabled=false
        binding.etSubjectProfile.isEnabled=false
        binding.etSchoolProfile.isEnabled=false
        binding.btnSaveProfile.isEnabled=false
        binding.btnEditProfile.isEnabled=true
    }
    private fun logOut() {
        firebaseAuth.signOut()
        checkUser()
    }
    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null) {
            val mail = firebaseUser.email
            binding.tvEmailProfile.text = mail
            db.collection("usuarios").document(mail.toString()).get().addOnSuccessListener {
                binding.etNameProfile.setText(it.get("nombre") as String?)
                binding.etSurnameProfile.setText(it.get("apellido") as String?)
                binding.etSubjectProfile.setText(it.get("materia") as String?)
                binding.etClassProfile.setText(it.get("curso") as String?)
                binding.etSchoolProfile.setText(it.get("escuela") as String?)
            }
        }else {intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}