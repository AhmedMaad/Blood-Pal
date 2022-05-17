package com.maad.bloodpal.donor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityAvailableHospitalsBinding
import com.maad.bloodpal.hospital.Hospital

class AvailableHospitalsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityAvailableHospitalsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableHospitalsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        //request all hospitals then sort using their location and the location chosen from previous activity
        //"lat", "lon"

    }

    override fun onResume() {
        super.onResume()
        val hospitals = arrayListOf<Hospital>()

        db
            .collection("users")
            .get()
            .addOnSuccessListener {
                binding.loadingTv.visibility = View.GONE
                for (hospital in it.documents)
                    if (hospital.get("userType") == "Hospital")
                        hospitals.add(hospital.toObject(Hospital::class.java)!!)
                val adapter = AvailableHospitalsAdapter(this, hospitals)
                binding.availableHospitalsRv.adapter = adapter
            }
    }

}