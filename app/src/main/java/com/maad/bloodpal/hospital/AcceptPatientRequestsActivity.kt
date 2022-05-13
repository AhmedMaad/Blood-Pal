package com.maad.bloodpal.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityAcceptPatientRequestsBinding

class AcceptPatientRequestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAcceptPatientRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}