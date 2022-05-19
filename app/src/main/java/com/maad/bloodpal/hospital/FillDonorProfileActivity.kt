package com.maad.bloodpal.hospital

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.bloodpal.RegisteredUser
import com.maad.bloodpal.databinding.ActivityFillDonorProfileBinding

class FillDonorProfileActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var db: FirebaseFirestore
    private var donors = arrayListOf<RegisteredUser>()
    private var emails = arrayListOf<String>()
    private var ids = arrayListOf<String>()
    private lateinit var binding: ActivityFillDonorProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillDonorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        binding.backBtn.setOnClickListener { finish() }

        val prefs = getSharedPreferences("user_settings", MODE_PRIVATE)
        val hospitalId = prefs.getString("id", null)!!

        db.collection("users")
            .get()
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                binding.saveProfileBtn.visibility = View.VISIBLE
                donors = it.toObjects(RegisteredUser::class.java) as ArrayList<RegisteredUser>
                for (donor in donors)
                    if (donor.userType == "Donor") {
                        emails.add(donor.email)
                        ids.add(donor.userId)
                    }

                val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, emails)
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.emailSpinner.adapter = adapter
            }

        binding.timeValue.setOnClickListener {
            val datePicker = DatePickerDialog()
            datePicker.isCancelable = false
            datePicker.show(supportFragmentManager, null)
        }

        val bloodTypes = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

        val bloodAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, bloodTypes)
        bloodAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.bloodTypeSpinner.adapter = bloodAdapter

        binding.saveProfileBtn.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            binding.saveProfileBtn.visibility = View.INVISIBLE
            val email = emails[binding.emailSpinner.selectedItemPosition]
            val date = binding.timeValue.text.toString()
            val bloodType = bloodTypes[binding.bloodTypeSpinner.selectedItemPosition]
            val hemoglobin = binding.hemoglobinEt.text.toString().toDouble()
            val hasPressure = binding.pressureCb.isChecked
            val donorId = ids[binding.emailSpinner.selectedItemPosition]
            Log.d("trace", "Donor ID: $donorId")
            val donorProfile =
                Donor(donorId, hospitalId, email, date, bloodType, hemoglobin, hasPressure)
            db.collection("donorHospital")
                .document(donorId)
                .set(donorProfile)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile Added", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = "$dayOfMonth-${(month + 1)}- $year"
        binding.timeValue.text = date
    }

}