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

        val services = arrayListOf<String>()
        services.add("Fill patients profile")
        services.add("Fill donors profile")
        services.add("View patients requests")
        services.add("Check late donors")

        val adapter = ServiceAdapter(this, services)
        binding.servicesRv.adapter = adapter

    }

}