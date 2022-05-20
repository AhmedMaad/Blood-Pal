package com.maad.bloodpal.hospital

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.RegisteredUser
import com.maad.bloodpal.databinding.ActivityFillDonorProfileBinding
import com.maad.bloodpal.databinding.ActivityFillPatientProfileBinding

class FillPatientProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var patients = arrayListOf<RegisteredUser>()
    private var emails = arrayListOf<String>()
    private var ids = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFillPatientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        binding.backBtn.setOnClickListener { finish() }

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
        val hospitalId = prefs.getString("id", null)!!

        //Retrieve "patients" email from firebase and show them in a spinner
        db.collection("users")
            .get()
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                binding.saveProfileBtn.visibility = View.VISIBLE
                patients = it.toObjects(RegisteredUser::class.java) as ArrayList<RegisteredUser>
                for (patient in patients)
                    if (patient.userType == "Patient") {
                        emails.add(patient.email)
                        ids.add(patient.userId)
                    }

                val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, emails)
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.emailSpinner.adapter = adapter
            }

        val bloodTypes = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

        val bloodAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, bloodTypes)
        bloodAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.bloodTypeSpinner.adapter = bloodAdapter

        binding.saveProfileBtn.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            binding.saveProfileBtn.visibility = View.INVISIBLE
            val bloodType = bloodTypes[binding.bloodTypeSpinner.selectedItemPosition]
            val bloodBags = binding.bloodBagsEt.text.toString()
            val diagnosis = binding.diagnosisEt.text.toString()
            val patientId = ids[binding.emailSpinner.selectedItemPosition]
            //Log.d("trace", "Donor ID: $patientId")

            val map = HashMap<String, String>()
            map["hospitalId"] = hospitalId
            map["bloodType"] = bloodType
            map["bloodBags"] = bloodBags
            map["diagnosis"] = diagnosis

            db.collection("users")
                .document(patientId)
                .update(map as Map<String, String>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile Added", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }

    }
}