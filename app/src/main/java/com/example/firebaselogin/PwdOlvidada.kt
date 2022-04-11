package com.example.firebaselogin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaselogin.databinding.ActivityPwdOlvidadaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class PwdOlvidada : AppCompatActivity() {
    lateinit var binding : ActivityPwdOlvidadaBinding
    private var email = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwdOlvidadaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btEnviarCodigo.setOnClickListener { sendKey() }
    }
    private fun sendKey() {
        email = binding.etEmailRecuperar.text.toString()
        Firebase.auth.isSignInWithEmailLink(email)
        Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Revise su buzón de correo electrónico", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Login::class.java))
                finish()
            }else{
                Toast.makeText(this, "No se pudo envíar el mail, intente nuevamente...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}