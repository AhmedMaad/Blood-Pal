package com.maad.bloodpal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

    }
}