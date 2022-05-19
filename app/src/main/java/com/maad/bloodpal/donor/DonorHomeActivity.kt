package com.maad.bloodpal.donor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.bloodpal.databinding.ActivityDonorHomeBinding
import com.maad.bloodpal.location.ChooseLocationActivity

class DonorHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDonorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.makeDonationBtn.setOnClickListener {
            val i = Intent(this, ChooseLocationActivity::class.java)
            //i.putExtra("requestType", "giveDonation")
            startActivity(i)
        }

        binding.donationWantedBtn.setOnClickListener {
            startActivity(Intent(this, HospitalBloodRequestsActivity::class.java))
        }

        binding.profileIv.setOnClickListener {
            startActivity(Intent(this, DonorProfileActivity::class.java))
        }

    }

}