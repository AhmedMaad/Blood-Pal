package com.maad.bloodpal.location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.R
import com.maad.bloodpal.databinding.ActivityChooseLocationBinding
import com.maad.bloodpal.donor.AvailableHospitalsActivity
import com.maad.bloodpal.donor.HospitalBloodRequestsActivity
import com.maad.bloodpal.patient.PatientHomeActivity
import com.maad.bloodpal.patient.PatientRequest

class ChooseLocationActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat = 0.0
    private var lon = 0.0
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.backBtn.setOnClickListener { finish() }

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)

        binding.confirmLocationBtn.setOnClickListener {
            //Determining userType
            val userType = prefs.getString("userType", null)

            val location = binding.locationTv.text.toString()
            when {
                location == "Get Location" -> {
                    Toast.makeText(this, "Click \"Get location\" first", Toast.LENGTH_LONG).show()
                    val animation = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
                    binding.locationContainer.startAnimation(animation)
                }
                userType == "Patient" -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.confirmLocationBtn.visibility = View.INVISIBLE

                    val userId = prefs.getString("id", null)
                    val health = intent.getStringArrayListExtra("health")

                    val request = PatientRequest(lat, lon, health!!, userId!!)
                    db
                        .collection("patientRequests")
                        .add(request)
                        .addOnSuccessListener {
                            val data = mutableMapOf<String, String>()
                            data["requestId"] = it.id
                            it.update(data as Map<String, Any>).addOnSuccessListener {
                                binding.progress.visibility = View.INVISIBLE
                                binding.confirmLocationBtn.visibility = View.VISIBLE
                                Toast.makeText(
                                    this,
                                    "Request sent, check notification icon",
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(Intent(this, PatientHomeActivity::class.java))
                                finish()
                            }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Error making the request",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show();
                                    binding.progress.visibility = View.INVISIBLE
                                    binding.confirmLocationBtn.visibility = View.VISIBLE
                                }
                        }

                }
                /*else -> {
                    val activity = when (intent.getStringExtra("requestType")) {
                        "giveDonation" -> AvailableHospitalsActivity::class.java
                        else -> HospitalBloodRequestsActivity::class.java //else "donationWanted"
                    }
                    val i = Intent(this, activity)
                    i.putExtra("lat", lat)
                    i.putExtra("lon", lon)
                    startActivity(i)
                }*/
                else -> {
                    val i = Intent(this, AvailableHospitalsActivity::class.java)
                    i.putExtra("lat", lat)
                    i.putExtra("lon", lon)
                    startActivity(i)
                }
            }

        }

        binding.locationContainer.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 101
                )
            } else {

                val animation = AnimationUtils.loadAnimation(this, R.anim.rotation)
                binding.mapIv.startAnimation(animation)

                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                            return CancellationTokenSource().token
                        }

                        override fun isCancellationRequested() = false
                    })
                    .addOnSuccessListener { location: Location? ->
                        binding.mapIv.clearAnimation()
                        if (location == null)
                            Toast.makeText(
                                this,
                                "Cannot get location, try again later",
                                Toast.LENGTH_SHORT
                            ).show()
                        else {
                            lat = location.latitude
                            lon = location.longitude
                            binding.locationTv.setText("$lat, $lon")
                        }
                    }

            }


        }

    }


}