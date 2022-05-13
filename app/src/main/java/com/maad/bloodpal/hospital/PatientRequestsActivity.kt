package com.maad.bloodpal.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityPatientRequestsBinding

class PatientRequestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPatientRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}