package com.maad.bloodpal.donor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityHospitalBloodRequestsBinding
import com.maad.bloodpal.hospital.BloodRequest
import com.maad.bloodpal.hospital.Hospital
import com.maad.bloodpal.hospital.HospitalProfileActivity

class HospitalBloodRequestsActivity : AppCompatActivity(), BloodRequestsAdapter.ItemClickListener {

    private lateinit var db: FirebaseFirestore
    private var bloodAndHospitals = arrayListOf<BloodAndHospital>()
    private var totalBloodRequests = 0
    private var counter = 0
    private lateinit var binding: ActivityHospitalBloodRequestsBinding
    private lateinit var adapter: BloodRequestsAdapter
    private lateinit var uId: String
    private var managedBloodRequests = arrayListOf<DonorRequestStatus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBloodRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
        uId = prefs.getString("id", null)!!

        //l kol blood requests mwgood ha3addy 3la el status el mwgood w 23ml check in case
        //en el status id == user id

        //0- return all status from firebase
        //1- loop through status and check if "status user id" == "fetched user id from prefs"
        //2- if true, then save "status blood request id" in an array
        //3- fetch all blood requests from firebase
        //4- check if "blood request id" == "status blood request id"
        //5- then

        db
            .collection("donorRequestsStatus")
            .get()
            .addOnSuccessListener {
                val statusRequests = it.toObjects(DonorRequestStatus::class.java)
                for (status in statusRequests)
                    if (status.userId == uId) {
                        managedBloodRequests.add(status)
                        Log.d("trace", "Repeated request")
                    }
                fetchAllBloodRequests()
            }


        /* db
             .collection("bloodRequests")
             .get()
             .addOnSuccessListener {
                 val bloodRequests = it.toObjects(BloodRequest::class.java)
                 totalBloodRequests = bloodRequests.size
                 for (request in bloodRequests) {
                     //Log.d("trace", "Looping through blood requests")
                     var flag = false
                     db
                         .collection("donorRequestsStatus")
                         .get()
                         .addOnSuccessListener {
                             val requestStatus = it.toObjects(DonorRequestStatus::class.java)
                             for (status in requestStatus) {
                                 if (status.userId == uId) {
                                     //only get associated hospital if request id is not the same in firebase
                                     flag  = true
                                 }
                             }
                             //check flag
                             if (!flag){
                                 Log.d("trace", "Getting hospital")
                                 getAssociatedHospital(request, request.hospitalId)
                             }
                             else{
                                 Log.d("trace", "Classhhhhh.....")
                             }

                         }

                 }
             }*/

    }

    private fun fetchAllBloodRequests() {
        db
            .collection("bloodRequests")
            .get()
            .addOnSuccessListener {
                val allBloodRequests = it.toObjects(BloodRequest::class.java)
                totalBloodRequests = allBloodRequests.size - managedBloodRequests.size
                for (bloodRequest in allBloodRequests){
                    var flag = false
                    for (managedRequest in managedBloodRequests){
                        if (bloodRequest.id == managedRequest.bloodRequest.id){
                            Log.d("trace", "Final Clash")
                            flag  = true
                        }
                    }
                    if (!flag){
                        Log.d("trace" , "Getting Associated Hospital")
                        getAssociatedHospital(bloodRequest)
                    }

                }


            }

    }

    private fun getAssociatedHospital(request: BloodRequest) {
        db
            .collection("users")
            .document(request.hospitalId)
            .get()
            .addOnSuccessListener {
                Log.d("trace", "Adding final data")
                val hospital = it.toObject(Hospital::class.java)!!
                bloodAndHospitals.add(BloodAndHospital(request, hospital))
                ++counter
                if (counter == totalBloodRequests) {
                    //Log.d("trace", "All requests received successfully")
                    binding.loadingTv.visibility = View.GONE
                    adapter = BloodRequestsAdapter(this, bloodAndHospitals, this)
                    binding.bloodRequestsRv.adapter = adapter
                }
            }
    }

    override fun onItemClick(position: Int) {
        val i = Intent(this, HospitalProfileActivity::class.java)
        i.putExtra("hospitalId", bloodAndHospitals[position].hospital.id)
        startActivity(i)
    }

    override fun onAcceptBtnClick(position: Int) {
        Log.d("trace", "Position from Accept: $position")
        sendStatus(
            "Accepted",
            position,
            bloodAndHospitals[position].bloodRequest
        )
    }

    override fun onDenyBtnClick(position: Int) {
        Log.d("trace", "Position from Deny: $position")
        sendStatus(
            "Denied",
            position,
            bloodAndHospitals[position].bloodRequest
        )
    }

    private fun sendStatus(status: String, positionToRemove: Int, bloodRequest: BloodRequest) {
        binding.progress.visibility = View.VISIBLE

        val requestStatus = DonorRequestStatus(bloodRequest, uId, status)
        db
            .collection("donorRequestsStatus")
            .add(requestStatus)
            .addOnSuccessListener {
                bloodAndHospitals.removeAt(positionToRemove)
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