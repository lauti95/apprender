package com.example.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaselogin.databinding.ActivityChangePwdBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class ChangePwdActivity : AppCompatActivity() {
    lateinit var binding : ActivityChangePwdBinding
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        binding.btnChangePwdCP.setOnClickListener {
            val currentPwd = binding.etCurrentPwdCP.text.toString()
            val newPwd = binding.etNewPwdCP.text.toString()
            val repeatPwd = binding.etRepeatPwdCP.text.toString()
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$"
            val passwordRegex = Pattern.compile(passwordPattern)

            if(newPwd.isEmpty() || !passwordRegex.matcher(newPwd).matches()){
                Toast.makeText(this, "La contraseña debe tener más de 6 caracteres, al menos 1 SIM, 1 MAY y 1 NUM", Toast.LENGTH_LONG).show()
            }else if (newPwd != repeatPwd){
                Toast.makeText(this, "Las contraseñas nuevas no coinciden", Toast.LENGTH_LONG).show()
            }else{
                changePassword(currentPwd, newPwd)
            }
        }
    }

    private fun changePassword(currentPwd: String, newPwd: String) {
        val user = firebaseAuth.currentUser
        if (user != null){
            val mail = user.email
            val credential = EmailAuthProvider.getCredential(mail!!, currentPwd)
            if(credential == null){
                Toast.makeText(this, "Los datos son inválidos", Toast.LENGTH_LONG).show()
            }
            else{
                user.reauthenticate(credential).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        user.updatePassword(newPwd).addOnCompleteListener { taskUpdateOK ->
                            if(taskUpdateOK.isSuccessful){
                                Toast.makeText(this, "Contraseña cambiada exitosamente", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } // End if taskUpdateOK
                        }
                    }else Toast.makeText(this, "Ha ocurrido un error en el servidor. Por favor, reintentar", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}