package com.maad.bloodpal.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.User
import com.maad.bloodpal.databinding.ActivityPatientRequestsBinding
import com.maad.bloodpal.donor.BloodAndHospital
import com.maad.bloodpal.donor.BloodRequestsAdapter
import com.maad.bloodpal.donor.DonorRequestStatus
import com.maad.bloodpal.patient.PatientRequest
import com.maad.bloodpal.patient.PatientRequestStatus

class PatientRequestsActivity : AppCompatActivity(), PatientRequestsAdapter.ItemClickListener {

    private lateinit var db: FirebaseFirestore
    private lateinit var uId: String
    private var totalBloodRequests = 0
    private var counter = 0
    private var managedPatientRequests = arrayListOf<PatientRequestStatus>()
    private var patientAndUser = arrayListOf<PatientAndUser>()
    private lateinit var binding: ActivityPatientRequestsBinding
    private lateinit var adapter: PatientRequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
        uId = prefs.getString("id", null)!!

        db
            .collection("patientRequestsStatus")
            .get()
            .addOnSuccessListener {
                val statusRequests = it.toObjects(PatientRequestStatus::class.java)
                for (status in statusRequests)
                    if (status.hospitalId == uId) {
                        managedPatientRequests.add(status)
                        Log.d("trace", "Repeated request")
                    }
                fetchAllPatientRequests()
            }

    }

    private fun fetchAllPatientRequests() {
        var managedCounter = 0
        db
            .collection("patientRequests")
            .get()
            .addOnSuccessListener {
                val allPatientRequests = it.toObjects(PatientRequest::class.java)
                totalBloodRequests = allPatientRequests.size - managedPatientRequests.size
                for (patientRequest in allPatientRequests){
                    var flag = false
                    for (managedRequest in managedPatientRequests){
                        if (patientRequest.userId == managedRequest.patientRequest.userId){
                            ++managedCounter
                            Log.d("trace", "Final Clash")
                            flag  = true
                        }
                    }
                    if (!flag){
                        Log.d("trace" , "Getting Associated Patient")
                        getAssociatedPatient(patientRequest)
                    }
                    else if (managedCounter == managedPatientRequests.size)
                        binding.loadingTv.text = "No requests are found"

                }

            }

    }

    private fun getAssociatedPatient(request: PatientRequest) {
        Log.d("trace", "Getting associated Patient")
        db
            .collection("users")
            .document(request.userId)
            .get()
            .addOnSuccessListener {
                Log.d("trace", "Adding final data")
                val user = it.toObject(User::class.java)!!
                patientAndUser.add(PatientAndUser(request, user))
                ++counter
                if (counter == totalBloodRequests) {
                    //Log.d("trace", "All requests received successfully")
                    binding.loadingTv.visibility = View.GONE
                    adapter = PatientRequestsAdapter(this, patientAndUser, this)
                    binding.availableHospitalsRv.adapter = adapter
                }
            }
    }

    override fun onAcceptBtnClick(position: Int) {
        Log.d("trace", "Position from Accept: $position")
        sendStatus(
            "Accepted",
            position,
            patientAndUser[position].patientRequest
        )
    }

    override fun onDenyBtnClick(position: Int) {
        Log.d("trace", "Position from Deny: $position")
        sendStatus(
            "Denied",
            position,
            patientAndUser[position].patientRequest
        )
    }

    private fun sendStatus(status: String, positionToRemove: Int, patientRequest: PatientRequest) {
        binding.progress.visibility = View.VISIBLE

        val requestStatus = PatientRequestStatus(patientRequest, uId, status)
        db
            .collection("patientRequestsStatus")
            .add(requestStatus)
            .addOnSuccessListener {
                patientAndUser.removeAt(positionToRemove)
                adapter.notifyItemRemoved(positionToRemove)
                binding.progress.visibility = View.GONE
                when (status) {
                    "Denied" -> Toast.makeText(this, "Request Denied", Toast.LENGTH_SHORT).show()
                    "Accepted" -> Toast.makeText(this, "Request Accepted", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

}