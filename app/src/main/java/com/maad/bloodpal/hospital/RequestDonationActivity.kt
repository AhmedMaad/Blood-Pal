package com.maad.bloodpal.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.maad.bloodpal.databinding.ActivityRequestDonationBinding

class RequestDonationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRequestDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //send hospital id with the request

        //increase "not specified" option in number of donors

        //val numberOfDonors = arrayOf("1", "2", "3", "4", "5", "Not Specified")
        val bloodTypes = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

        /*val donorsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numberOfDonors)
        donorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.neededDonorsSpinner.adapter = donorsAdapter*/

        val bloodAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodTypes)
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.bloodTypeSpinner.adapter = bloodAdapter


    }
}