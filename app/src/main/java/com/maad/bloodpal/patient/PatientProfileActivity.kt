package com.maad.bloodpal.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityPatientProfileBinding

class PatientProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPatientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}