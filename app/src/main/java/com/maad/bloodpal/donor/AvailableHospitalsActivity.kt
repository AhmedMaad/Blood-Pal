package com.maad.bloodpal.donor

import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityAvailableHospitalsBinding
import com.maad.bloodpal.hospital.Hospital
import java.util.*

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
                for (hospital in it.documents)
                    if (hospital.get("userType") == "Hospital")
                        hospitals.add(hospital.toObject(Hospital::class.java)!!)

                val sortedHospitals = arrayListOf<Hospital>()
                val userLocation = Location(LocationManager.GPS_PROVIDER)
                userLocation.latitude = intent.getDoubleExtra("lat", 0.0)
                userLocation.longitude = intent.getDoubleExtra("lon", 0.0)
                //Calculating all distances in meters based on user location
                for (hospital in hospitals) {
                    val hospitalLocation = Location(LocationManager.GPS_PROVIDER)
                    hospitalLocation.latitude = hospital.lat
                    hospitalLocation.longitude = hospital.lon
                    val distance = userLocation.distanceTo(hospitalLocation)
                    Log.d("trace", "Distance $distance")
                    sortedHospitals.add(
                        Hospital(
                            hospital.id,
                            hospital.userType,
                            hospital.website,
                            hospital.name,
                            hospital.address,
                            hospital.phoneNumber,
                            hospital.businessHours,
                            hospital.businessDays,
                            hospital.lat,
                            hospital.lon,
                            hospital.logo,
                            hospital.totalRating,
                            hospital.ratingCounter,
                            hospital.finalRating,
                            distance.toDouble()
                        )
                    )
                }
                sortedHospitals.sort()
                val adapter = AvailableHospitalsAdapter(this, sortedHospitals)
                binding.availableHospitalsRv.adapter = adapter
                binding.loadingTv.visibility = View.GONE
            }
    }


}