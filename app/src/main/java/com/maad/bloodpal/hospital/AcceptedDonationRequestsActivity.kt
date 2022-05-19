package com.maad.bloodpal.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.User
import com.maad.bloodpal.databinding.ActivityAcceptedDonationRequestsBinding
import com.maad.bloodpal.donor.DonorRequestStatus

class AcceptedDonationRequestsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private val acceptedRequests = arrayListOf<DonorRequestStatus>()
    private lateinit var binding: ActivityAcceptedDonationRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcceptedDonationRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
        val hospitalId = prefs.getString("id", null)!!

        db
            .collection("donorRequestsStatus")
            .get()
            .addOnSuccessListener {
                val allRequests = it.toObjects(DonorRequestStatus::class.java)
                for (request in allRequests)
                    if (request.status == "Accepted" && request.bloodRequest.hospitalId == hospitalId)
                        acceptedRequests.add(request)
                getAcceptedRequests()
            }

    }

    private fun getAcceptedRequests() {
        var counter = 0
        val usersList = arrayListOf<AcceptedDonation>()
        for (request in acceptedRequests)
            db.collection("users")
                .document(request.userId)
                .get()
                .addOnSuccessListener {
                    ++counter
                    val user = it.toObject(User::class.java)!!
                    usersList.add(AcceptedDonation(user, request.bloodRequest.bloodType))
                    if (counter == acceptedRequests.size) {
                        binding.loadingTv.visibility = View.GONE
                        val adapter = AcceptedDonationAdapter(this, usersList)
                        binding.acceptedDonationsRv.adapter = adapter
                    }
                }
    }

}