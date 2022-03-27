package com.example.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaselogin.databinding.ActivityForgotPwdBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPwdActivity : AppCompatActivity() {
    lateinit var binding : ActivityForgotPwdBinding
    private var email = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSendKeyFP.setOnClickListener { sendKey() }
    }

    private fun sendKey() {
        email = binding.etEmailFP.text.toString()
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