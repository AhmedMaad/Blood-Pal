package com.maad.bloodpal.registeration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityLoginBinding
import com.maad.bloodpal.donor.DonorHomeActivity
import com.maad.bloodpal.patient.PatientHomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    var userType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        binding.backBtn.setOnClickListener { finish() }

        binding.loginBtn.setOnClickListener {
            //val email = binding.emailEt.text.toString()
            //val password = binding.passwordEt.text.toString()
            val email = "p1@gmail.com"
            val password = "123456"
            if (email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Important Fields are missing", Toast.LENGTH_SHORT).show()
            else {
                binding.loginBtn.visibility = View.INVISIBLE
                binding.progress.visibility = View.VISIBLE
                val auth: FirebaseAuth = Firebase.auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            binding.loginBtn.visibility = View.VISIBLE
                            binding.progress.visibility = View.INVISIBLE
                            getData(task.result.user!!.uid)

                        } else {
                            binding.loginBtn.visibility = View.VISIBLE
                            binding.progress.visibility = View.INVISIBLE
                            Toast.makeText(
                                this, "Login failed, ${task.exception?.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        binding.forgotPasswordTv.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val i = Intent(this, ForgotPasswordActivity::class.java)
            if (email.isNotEmpty())
                i.putExtra("email", email)
            startActivity(i)
        }

    }

    private fun getData(id: String) {

        db
            .collection("users")
            .document(id)
            .get()
            .addOnSuccessListener {
                userType = it.get("userType") as String

                val editor = getSharedPreferences("user_settings", MODE_PRIVATE).edit()
                editor.putString("id", id)
                editor.putString("userType", userType)
                editor.apply()

                when (userType) {
                    "Donor" -> {
                        val i = Intent(this, DonorHomeActivity::class.java)
                        startActivity(i)
                        finishAffinity()
                    }
                    "Patient" -> {
                        val i = Intent(this, PatientHomeActivity::class.java)
                        startActivity(i)
                        finishAffinity()
                    }
                }
            }

    }

}