package com.maad.bloodpal.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityHealthStatusBinding

class HealthStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHealthStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}