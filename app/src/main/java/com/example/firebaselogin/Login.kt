package com.example.firebaselogin
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.text.TextUtilsCompat
import androidx.core.text.trimmedLength
import com.example.firebaselogin.databinding.ActivityLoginBinding
import com.example.firebaselogin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.w3c.dom.Text
import java.util.regex.Pattern
class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    private val GOOGLE_SIGN = 100
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.hide()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor, espere...")
        progressDialog.setMessage("Iniciando sesión...")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth = FirebaseAuth.getInstance()
        userCheck()
        binding.btnLoginLogin.setOnClickListener{validacionUser()}
        binding.etToRegister.setOnClickListener {startActivity(Intent(this, Registro::class.java))}
        binding.etGoogleLogin.setOnClickListener {iniciarGoogle()}
        binding.tvForgotPwd.setOnClickListener {startActivity(Intent(this, ForgotPwdActivity::class.java))}
    }

    private fun iniciarGoogle() {
        val google = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, google)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        progressDialog.show()
        if(requestCode == GOOGLE_SIGN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                if(account != null){
                    val credencial = GoogleAuthProvider.getCredential(account.idToken, null)
                    firebaseAuth.signInWithCredential(credencial).addOnCompleteListener {
                        if(it.isSuccessful){
                            progressDialog.dismiss()
                            startActivity(Intent(this, MainActivity::class.java))
                        }else{
                            progressDialog.dismiss()
                            Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: ApiException){
                progressDialog.dismiss()
                Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validacionUser() {
        email = binding.etEmailLogin.text.toString().trim()
        password = binding.etPwdLogin.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailLogin.setError("Email inválido")
        }
        else if(!password.contains(Regex("[0-9]"))){
            binding.etPwdLogin.setError("Debe contener al menos un número")
        }
        else if(!password.contains(Regex("[^a-zA-Z0-9 ]"))){
            binding.etPwdLogin.setError("Debe contener al menos un caracter especial")
        }
        else if(!password.contains(Regex("[A-Z]"))){
            binding.etPwdLogin.setError("Debe contener al menos una mayúscula")
        }
        else if(password.length < 6){
            binding.etPwdLogin.setError("Debe ser mayor a 6 caracteres")
        }
        else if(TextUtils.isEmpty(password)){
            binding.etPwdLogin.setError("No puede ser vacía")
        }
        else{firebaseLogin()}
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                var mail = firebaseUser!!.email
                Toast.makeText(this, "Bienvenido, $mail", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener{
                e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Usuario o contraseña inválidos", Toast.LENGTH_LONG).show()
            }
    }

    private fun userCheck() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}