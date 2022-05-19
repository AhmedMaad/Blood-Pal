package com.maad.bloodpal.registeration

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.maad.bloodpal.RegisteredUser
import com.maad.bloodpal.User
import com.maad.bloodpal.databinding.ActivityUserTypeBinding
import com.maad.bloodpal.donor.DonorHomeActivity
import com.maad.bloodpal.patient.PatientHomeActivity
import java.util.*

class UserTypeActivity : AppCompatActivity() {

    private lateinit var user: User
    private var userType = ""
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityUserTypeBinding
    private var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        user = intent.getParcelableExtra("user")!!

        binding.backBtn.setOnClickListener { finish() }

        binding.donorBtn.setOnClickListener { clickButton("Donor") }

        binding.patientBtn.setOnClickListener { clickButton("Patient") }

    }

    private fun authenticateUser() {
        val auth: FirebaseAuth = Firebase.auth
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(this) { task ->
                Log.d("trace", "Authenticating user...")
                if (task.isSuccessful) {
                    userId = task.result.user!!.uid
                    uploadImage()
                } else {
                    binding.progress.visibility = View.INVISIBLE
                    binding.donorBtn.isEnabled = true
                    binding.patientBtn.isEnabled = true
                    Toast.makeText(this, "User Failed to be added", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadImage() {
        Log.d("trace", "Uploading Image...")
        val storage = FirebaseStorage.getInstance()
        val now: Calendar = Calendar.getInstance()
        val y: Int = now.get(Calendar.YEAR)
        val m: Int = now.get(Calendar.MONTH) + 1

        val d: Int = now.get(Calendar.DAY_OF_MONTH)
        val h: Int = now.get(Calendar.HOUR_OF_DAY)
        val min: Int = now.get(Calendar.MINUTE)
        val s: Int = now.get(Calendar.SECOND)
        val imageName = "image: $d-$m-$y $h:$min:$s"

        val storageRef = storage.reference.child(imageName)
        storageRef.putFile(user.picture.toUri())
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    Log.d("trace", "Image Uploaded: $it")
                    addUser(it)
                }
            }
    }

    private fun addUser(imageUri: Uri?) {
        Log.d("trace", "Adding User")

        val registeredUser = RegisteredUser(
            userId,
            userType,
            imageUri.toString(),
            user.email,
            user.password,
            user.mobile,
            user.idNumber,
            user.gender
        )

        db
            .collection("users")
            .document(userId)
            .set(registeredUser)
            .addOnSuccessListener {
                //Log.d("trace", "User Added Successfully")
                binding.progress.visibility = View.INVISIBLE
                binding.donorBtn.isEnabled = true
                binding.patientBtn.isEnabled = true
                Toast.makeText(this, "User Added Successfully", Toast.LENGTH_LONG).show()

                val editor = getSharedPreferences("user_settings", MODE_PRIVATE).edit()
                editor.putString("id", userId)
                editor.putString("userType",userType)
                editor.apply()

                when(userType){
                    "Donor" ->{
                        val i = Intent(this, DonorHomeActivity::class.java)
                        startActivity(i)
                        finishAffinity()
                    }
                    "Patient" ->{
                        val i = Intent(this, PatientHomeActivity::class.java)
                        startActivity(i)
                        finishAffinity()
                    }
                }

            }

    }

    private fun clickButton(userType: String) {
        //Log.d("trace", "Button Clicked")
        binding.progress.visibility = View.VISIBLE
        binding.donorBtn.isEnabled = false
        binding.patientBtn.isEnabled = false
        this.userType = userType
        authenticateUser()
    }

}