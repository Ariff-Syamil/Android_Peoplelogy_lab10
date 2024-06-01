package com.example.lab10

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab10.databinding.ActivitySignupBinding
import com.google.android.material.snackbar.Snackbar

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private lateinit var  auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            createUser(binding.signupEmailEditText.text.toString(),
                binding.signupPasswordEditText.text.toString())
        }

    }

    fun createUser(email:String, password:String) {
        // createUser use email and password
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                val intent = Intent(this, ThankyouActivity::class.java)
                startActivity(intent)
            }
            else {
                Snackbar.make(
                    binding.root,
                    "Enter a valid username and password",
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}