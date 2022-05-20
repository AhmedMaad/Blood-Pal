package com.maad.bloodpal.patient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityPatientHomeBinding
import com.maad.bloodpal.hospital.Hospital
import com.maad.bloodpal.hospital.HospitalProfileActivity


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
            acceptedRequests.clear()
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
        val names = arrayListOf<String>()
        val ids = arrayListOf<String>()
        for (request in acceptedRequests)
            db.collection("users")
                .document(request.hospitalId)
                .get()
                .addOnSuccessListener {
                    ++counter
                    val hospital = it.toObject(Hospital::class.java)!!
                    names.add("\"${hospital.name}\" accepted your request")
                    ids.add(hospital.id)
                    if (counter == acceptedRequests.size) {
                        snackBar.dismiss()
                        getPopup(names, ids)
                    }
                }
    }

    private fun getPopup(names: ArrayList<String>, ids: ArrayList<String>) {
        val popupMenu = PopupMenu(this, binding.notificationIv)
        for (i in 0 until names.size) {
            popupMenu.menu.add(i, Menu.FIRST, i, names[i])
        }
        popupMenu.setOnMenuItemClickListener { item ->
            //Log.d("trace", "ID: ${ids[item.order]}")
            val i = Intent(this, HospitalProfileActivity::class.java)
            i.putExtra("hospitalId", ids[item.order])
            startActivity(i)
            false
        }
        popupMenu.show()
    }

}