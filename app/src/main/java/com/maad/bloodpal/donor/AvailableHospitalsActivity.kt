package com.maad.bloodpal.donor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityAvailableHospitalsBinding

class AvailableHospitalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAvailableHospitalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.foldingCell.setOnClickListener {
            binding.foldingCell.toggle(false)
        }

    }

}