package com.maad.bloodpal.hospital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityHospitalHomeBinding

class HospitalHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHospitalHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileIv.setOnClickListener {
            startActivity(Intent(this, HospitalProfileActivity::class.java))
        }

        //"Fill patient profile - fill donor profile
        // - accept patient requests - send email to late donors

    }
}