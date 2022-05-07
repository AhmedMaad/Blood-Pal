package com.maad.bloodpal.location

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.maad.bloodpal.R
import com.maad.bloodpal.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.confirmLocationBtn.setOnClickListener {
            val location = binding.locationTv.text.toString()
            if (location == "Enter Location") {
                Toast.makeText(this, "Choose location first", Toast.LENGTH_SHORT).show();
                val animation = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
                binding.locationContainer.startAnimation(animation)
            } else {
                //TODO: get requestType before navigating to all hospitals or requests made by hospitals
                //send lat, lng to next activity
            }

        }

        binding.locationContainer.setOnClickListener {
            val i = Intent(this, MapsActivity::class.java)
            startActivityForResult(i, 101)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

}