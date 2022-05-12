package com.maad.bloodpal.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityPatientHomeBinding

class PatientHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPatientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileIv.setOnClickListener {
            startActivity(Intent(this, PatientProfileActivity::class.java))
        }

        binding.makeRequestBtn.setOnClickListener {

        }

    }
}