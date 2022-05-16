package com.maad.bloodpal.donor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityAvailableHospitalsBinding

class AvailableHospitalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAvailableHospitalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hide loading text view after data is fetched from server

        //request all hospitals then sort using their location and the location chosen from previous activity

    }

}