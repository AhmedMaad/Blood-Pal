package com.maad.bloodpal.donor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.R
import com.maad.bloodpal.RegisteredUser
import com.maad.bloodpal.databinding.ActivityDonorProfileBinding
import com.maad.bloodpal.databinding.ActivityPatientProfileBinding

class DonorProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityDonorProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        binding.backBtn.setOnClickListener { finish() }

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
        val id = prefs.getString("id", null)!!

        db
            .collection("users")
            .document(id)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(RegisteredUser::class.java)!!
                Glide.with(this).load(user.picture).into(binding.profileIv)
                when (user.gender) {
                    "Male" -> binding.genderIv.setImageResource(R.drawable.ic_male)
                    "Female" -> binding.genderIv.setImageResource(R.drawable.ic_female)
                }
                binding.emailTv.text = user.email
                binding.idValueTv.text = user.idNumber
                binding.phoneValueTv.text = user.mobile
                binding.bloodTypeTv.text = it.getString("bloodType") ?: "No Defined Blood Type"
                binding.hemoglobinValueTv.text =
                    it.getString("hemoglobin") ?: "No Information Available"
                binding.donationValueTv.text = it.getString("date") ?: "No Information Available"
                binding.pressureValueTv.text =
                    it.getString("hasPressure") ?: "No Information Available"
                getHospitalName(it.getString("hospitalId") ?: "No Information Available")
            }

    }

    private fun getHospitalName(hospitalId: String) {
        if (hospitalId == "No Information Available") {
            binding.filledByValueTv.text = "No Information Available"
            binding.progress.visibility = View.GONE
        } else
            db
                .collection("users")
                .document(hospitalId)
                .get()
                .addOnSuccessListener {
                    binding.filledByValueTv.text = it.getString("name")
                    binding.progress.visibility = View.GONE
                }

    }

}