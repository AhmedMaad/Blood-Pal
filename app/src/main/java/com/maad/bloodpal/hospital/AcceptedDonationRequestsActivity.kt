package com.maad.bloodpal.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityAcceptedDonationRequestsBinding

class AcceptedDonationRequestsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAcceptedDonationRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}