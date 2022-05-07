package com.maad.bloodpal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener { finish() }

        binding.emailEt.setText(intent.getStringExtra("email"))

        binding.resetBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            if (email.isEmpty())
                Toast.makeText(this, "You have to write your email", Toast.LENGTH_SHORT).show()
            else {
                binding.resetBtn.visibility = View.INVISIBLE
                binding.progress.visibility = View.VISIBLE
                Firebase.auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
            }

        }

    }

}