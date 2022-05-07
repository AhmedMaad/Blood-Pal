package com.maad.bloodpal.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityPatientHomeBinding

class PatientHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPatientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}