package com.maad.bloodpal.donor

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.R
import com.maad.bloodpal.databinding.ActivityHospitalProfileBinding
import com.maad.bloodpal.hospital.Hospital
import kotlin.math.roundToInt

class DonorHospitalProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHospitalProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        binding.progress.visibility = View.GONE
        binding.requestEditBtn.visibility = View.GONE
        binding.submitRatingBtn.visibility = View.VISIBLE
        binding.ratingBar.visibility = View.VISIBLE

        binding.addressValueTv.setTextColor(ActivityCompat.getColor(this, R.color.blue))
        binding.addressValueTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        val hospital: Hospital = intent.getParcelableExtra("hospital")!!

        binding.ratingTv.text = "%.2f".format(hospital.finalRating)
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

        binding.websiteTv.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, hospital.website.toUri()))
        }

        binding.addressValueTv.setOnClickListener {
            val mapIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    "google.navigation:q=${hospital.lat},${hospital.lon}".toUri()
                )
            startActivity(mapIntent)
        }



        binding.submitRatingBtn.setOnClickListener {
            //update current rating for the specific hospital, and show the new updated rating
            //in the current activity, and in the recycler view "override on resume"

            if (binding.ratingBar.rating == 0.0f)
                Toast.makeText(this, "Rating cannot be ZERO", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.visibility = View.VISIBLE
                binding.submitRatingBtn.visibility = View.INVISIBLE
                val finalRating =
                    (hospital.totalRating + binding.ratingBar.rating) / (hospital.ratingCounter + 1)

                val data: HashMap<String, Number> = HashMap()
                data["finalRating"] = finalRating
                data["ratingCounter"] = hospital.ratingCounter + 1
                data["totalRating"] = hospital.totalRating + binding.ratingBar.rating

                db
                    .collection("users")
                    .document(hospital.id)
                    .update(data as Map<String, Number>)
                    .addOnSuccessListener {
                        binding.progress.visibility = View.GONE
                        binding.submitRatingBtn.visibility = View.VISIBLE
                        Toast.makeText(this, "Rating submitted", Toast.LENGTH_SHORT).show()
                        finish()
                    }
            }

            //Input:
            //average + currentVote / (totalVoters + anotherVote)

            //5 + 2.5 + 2.5

        }


    }
}