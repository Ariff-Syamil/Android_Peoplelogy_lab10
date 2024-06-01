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

    private lateinit var db: FirebaseFirestore;

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

        db = Firebase.firestore

    }

    fun createUser(email:String, password:String) {
        // createUser use email and password
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                newCustomer()
            }
            else {
                Snackbar.make(
                    binding.root,
                    "Enter a valid username and password",
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }.addOnFailureListener { err->
            Log.d("debug", err.stackTraceToString())
            Snackbar.make(binding.root,
                err.message!!,
                Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun newCustomer() {

        val custommer = hashMapOf(
            "name" to binding.signupNameEditText.text.toString().trim(),
            "city" to binding.signupCityEditText.text.toString().trim(),
            "country" to binding.signupCountryEditText.toString().trim(),
            "phone" to binding.signupPhoneEditText.toString().trim(),

        )

        db.collection("customers")
            .add(customer)
            .addOnSuccessListener {
                documentReference ->
                Log.d("debug", "Document successfully added with id ${documentReference.id}")
                val intent = Intent(this, ThankyouActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { err->
            Log.d("debug", "An error happen ${e.message}")
        }
    }
}