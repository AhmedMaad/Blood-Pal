package com.maad.bloodpal.hospital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityHospitalProfileBinding

class HospitalProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var hospital: Hospital

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHospitalProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
        val id = prefs.getString("id", null)

        db
            .collection("users")
            .document(id!!)
            .get()
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                hospital = it.toObject(Hospital::class.java)!!
                binding.ratingTv.text = hospital.rating.toString()
                binding.nameTv.text = hospital.name
                binding.websiteTv.text = hospital.website
                binding.addressValueTv.text = hospital.address
                binding.phoneValueTv.text = hospital.phoneNumber
                binding.hoursValueTv.text = hospital.businessHours
                binding.daysValueTv.text = hospital.businessDays
                Glide
                    .with(this)
                    .load(hospital.logo)
                    .into(binding.profileIv)
            }

        binding.requestEditBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "*/*"
            i.putExtra(Intent.EXTRA_SUBJECT, "Request Profile Edit for \"${hospital.name}\"")
            startActivity(i)
        }

        binding.websiteTv.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, hospital.website.toUri()))
        }


    }

}