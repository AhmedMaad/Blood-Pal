package com.maad.bloodpal.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityRequestDonationBinding

class RequestDonationActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRequestDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val bloodTypes = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

        val bloodAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodTypes)
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.bloodTypeSpinner.adapter = bloodAdapter

        binding.backBtn.setOnClickListener { finish() }

        binding.submitRequestBtn.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            binding.submitRequestBtn.visibility = View.INVISIBLE

            val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
            val hospitalId = prefs.getString("id", null)!!
            val request = BloodRequest(
                hospitalId = hospitalId,
                bloodType = bloodTypes[binding.bloodTypeSpinner.selectedItemPosition]
            )

            db
                .collection("bloodRequests")
                .add(request)
                .addOnSuccessListener {
                    val map = HashMap<String, String>()
                    map["id"] = it.id
                    it.update(map as Map<String, String>).addOnSuccessListener {
                        binding.progress.visibility = View.INVISIBLE
                        binding.submitRequestBtn.visibility = View.VISIBLE
                        Toast.makeText(this, "Request Sent to all users", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }

        }


    }
}