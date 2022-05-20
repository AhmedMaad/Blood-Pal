package com.maad.bloodpal.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.User
import com.maad.bloodpal.databinding.ActivityAcceptedDonationRequestsBinding
import com.maad.bloodpal.databinding.ActivityPatientHomeBinding
import com.maad.bloodpal.donor.DonorRequestStatus
import com.maad.bloodpal.hospital.AcceptedDonationAdapter
import com.maad.bloodpal.hospital.Donation
import com.maad.bloodpal.hospital.Hospital

class PatientHomeActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private val acceptedRequests = arrayListOf<PatientRequestStatus>()
    private lateinit var binding: ActivityPatientHomeBinding
    private lateinit var snackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
        val patientId = prefs.getString("id", null)!!
        //Log.d("trace", "Patient ID: $patientId")

        binding.profileIv.setOnClickListener {
            startActivity(Intent(this, PatientProfileActivity::class.java))
        }

        binding.makeRequestBtn.setOnClickListener {
            startActivity(Intent(this, HealthStatusActivity::class.java))
        }

        binding.notificationIv.setOnClickListener {
            snackBar = Snackbar.make(
                binding.root,
                "Please Wait...",
                BaseTransientBottomBar.LENGTH_INDEFINITE
            )
            snackBar.show()
            db.collection("patientRequestsStatus").get().addOnSuccessListener {
                val allRequests = it.toObjects(PatientRequestStatus::class.java)
                //Log.d("trace", "All requests size: ${allRequests.size}")
                for (request in allRequests) {
                    //Log.d("trace", "Status: ${request.status}, id: ${request.patientRequest.userId}")
                    if (request.status == "Accepted" && request.patientRequest.userId == patientId) {
                        //Log.d("trace", "Accepted Request Found")
                        acceptedRequests.add(request)
                    }
                }

                if (acceptedRequests.size == 0)
                    Toast.makeText(this, "Make a request first", Toast.LENGTH_SHORT).show();
                else
                    getAcceptedRequests()
            }

        }

    }

    private fun getAcceptedRequests() {
        var counter = 0
        val hospitalsList = arrayListOf<Hospital>()
        for (request in acceptedRequests)
            db.collection("users")
                .document(request.hospitalId)
                .get()
                .addOnSuccessListener {
                    ++counter
                    val hospital = it.toObject(Hospital::class.java)!!
                    hospitalsList.add(Hospital(name = hospital.name, id = hospital.id))
                    if (counter == acceptedRequests.size) {
                        snackBar.dismiss()
                        //show in list view with pink background as a popup menu
                        //then navigate to hospital profile on click
                    }
                }
    }

}