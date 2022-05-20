package com.maad.bloodpal.hospital

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.databinding.ActivityLateDonorsBinding
import java.text.SimpleDateFormat
import java.util.*

class LateDonorsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var lateDonors = arrayListOf<LateDonor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLateDonorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        //hide loading when data is received
        db.collection("users").get().addOnSuccessListener {
            for (user in it.documents)
                if (user.getString("userType") == "Donor" && user.getString("date") != null) {
                    val c = Calendar.getInstance()
                    val currentYear = c.get(Calendar.YEAR)
                    val currentMonth = c.get(Calendar.MONTH) + 1

                    val formatter = SimpleDateFormat("dd-MM-yyyy")
                    val text = user.getString("date")!!
                    val date = formatter.parse(text)

                    val c2 = Calendar.getInstance()
                    c2.time = date
                    val chosenYear = c2.get(Calendar.YEAR)
                    val chosenMonth = c2.get(Calendar.MONTH) + 1
                    //Log.d("trace", "$currentYear - $chosenYear = ${(currentYear - chosenYear)}")

                    if ((currentYear - chosenYear > 0) || ((currentYear - chosenYear == 0) && (currentMonth - chosenMonth > 3)))
                        lateDonors.add(
                            LateDonor(
                                user.getString("email")!!,
                                user.getString("date")!!
                            )
                        )

                }
            if (lateDonors.size == 0)
                binding.loadingTv.text = "No Late Donors"
            else {
                val adapter = LateDonorAdapter(this, lateDonors)
                binding.lateDonorsRv.adapter = adapter
                binding.loadingTv.visibility = View.GONE
            }

        }


    }
}