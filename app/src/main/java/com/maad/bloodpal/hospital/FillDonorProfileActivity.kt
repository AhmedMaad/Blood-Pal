package com.maad.bloodpal.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityFillDonorProfileBinding

class FillDonorProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFillDonorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}