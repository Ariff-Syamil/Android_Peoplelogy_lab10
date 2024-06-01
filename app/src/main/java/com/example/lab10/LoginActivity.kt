package com.example.lab10

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab10.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var  auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            if (binding.loginEmailEditText.text.trim().toString().isNotEmpty() ||
                binding.loginPasswordEditText.text.trim().toString().isNotEmpty()){
                loginUser(binding.loginEmailEditText.text.toString(),
                    binding.loginPasswordEditText.text.toString())
            }
            else {
                Snackbar.make(
                    binding.root,
                    "Please check your username and password",
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun loginUser(email: String, password: String){
        auth.signInWithEmailandPassword(email,password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                val intent = Intent(this, ServiceActivity::class.java)
                startActivity(intent)
            }
            else {
                Snackbar.make(
                    binding.root,
                    "Please check your username and password",
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }

            .addOnFailureListener { err->
                Log.d("debug",err.toString())
                Snackbar.make(
                    binding.root,
                    err.message!!,
                    Snackbar.LENGTH_LONG)
                    .show()
            }
    }
}