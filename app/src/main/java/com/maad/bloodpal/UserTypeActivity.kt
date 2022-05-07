package com.maad.bloodpal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityUserTypeBinding

class UserTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener { finish() }

        binding.donorBtn.setOnClickListener {
            //Register donor email and add data to firestore
        }

        binding.patientBtn.setOnClickListener {
            //Register patient email and add data to firestore
        }

    }
}