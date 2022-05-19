package com.maad.bloodpal.donor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityHospitalBloodRequestsBinding
import com.maad.bloodpal.hospital.BloodRequest
import com.maad.bloodpal.hospital.Hospital

class HospitalBloodRequestsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var bloodAndHospitals = arrayListOf<BloodAndHospital>()
    private var totalBloodRequests = 0
    private var counter = 0
    private lateinit var binding: ActivityHospitalBloodRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBloodRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        //hide "loading.." after data is fetched
        //make the recycler view listener in the activity "Accept / Deny"

        db
            .collection("bloodRequests")
            .get()
            .addOnSuccessListener {
                val bloodRequests = it.toObjects(BloodRequest::class.java)
                totalBloodRequests = bloodRequests.size
                for (request in bloodRequests) {
                    Log.d("trace", "Loop through blood requests")
                    getAssociatedHospital(request, request.hospitalId)
                }
            }

    }

    private fun getAssociatedHospital(request: BloodRequest, hospitalId: String) {
        db
            .collection("users")
            .document(hospitalId)
            .get()
            .addOnSuccessListener {
                Log.d("trace", "Adding final data")
                val hospital = it.toObject(Hospital::class.java)!!
                bloodAndHospitals.add(BloodAndHospital(request, hospital))
                ++counter
                if (counter == totalBloodRequests){
                    Log.d("trace", "All requests received successfully")
                    binding.loadingTv.visibility = View.GONE
                    val adapter = BloodRequestsAdapter(this, bloodAndHospitals)
                    binding.bloodRequestsRv.adapter = adapter
                }
            }
    }

}