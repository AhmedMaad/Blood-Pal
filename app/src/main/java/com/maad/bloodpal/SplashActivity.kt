package com.maad.bloodpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivitySplashBinding
import com.maad.bloodpal.registeration.LoginActivity
import com.maad.bloodpal.registeration.SignUpActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginTv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.aboutIv.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

    }
}