package com.maad.bloodpal.registeration

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.maad.bloodpal.User
import com.maad.bloodpal.databinding.ActivitySignUpBinding
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genders = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderSpinner.adapter = adapter

        binding.profileIv.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(i, 101)
        }

        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val mobile = binding.phoneEt.text.toString()
            val idNo = binding.idEt.text.toString()
            val gender = genders[binding.genderSpinner.selectedItemPosition]

            //if user was born in 90's, then any age is valid like "50"
            val age = if (idNo[0] == '2') 50
            else {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                val yearOfBirth = "20${idNo[1]}${idNo[2]}".toInt()
                currentYear - yearOfBirth
            }

            if (email.isEmpty() || password.isEmpty() || mobile.isEmpty()
                || idNo.isEmpty() || imageUri == null
            )
                Toast.makeText(this, "Important Fields are Missing", Toast.LENGTH_SHORT).show()
            else if (idNo.length != 14)
                Toast.makeText(this, "ID number should be 14 digits", Toast.LENGTH_SHORT)
                    .show()
            else if (age < 18) {
                Toast.makeText(this, "You have to be at least 18 years", Toast.LENGTH_SHORT).show();
                //val month = "${idNo[3]}${idNo[4]}".toInt()
                //val day = "${idNo[5]}${idNo[6]}"
            } else {
                val user = User(imageUri.toString(), email, password, mobile, idNo, gender)
                val i = Intent(this, UserTypeActivity::class.java)
                i.putExtra("user", user)
                startActivity(i)
            }

        }

        binding.backBtn.setOnClickListener { finish() }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 101) {
            imageUri = data?.data
            binding.profileIv.setImageURI(imageUri)
        }

    }

}