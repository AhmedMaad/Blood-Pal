package com.maad.bloodpal.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.maad.bloodpal.databinding.ActivityHealthStatusBinding
import com.maad.bloodpal.location.ChooseLocationActivity

class HealthStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHealthStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {
            if (!binding.cancerCb.isChecked && !binding.emergencyCb.isChecked
                && !binding.pressureCb.isChecked
            )
                Toast.makeText(this, "Choose your health status", Toast.LENGTH_SHORT).show()
            else {
                val health = arrayListOf<String>()
                if (binding.emergencyCb.isChecked)
                    health.add("Emergency")
                if (binding.cancerCb.isChecked)
                    health.add("Blood Cancer")
                if (binding.pressureCb.isChecked)
                    health.add("Pressure and Diabetes")

                val i = Intent(this, ChooseLocationActivity::class.java)
                i.putExtra("health", health)
                startActivity(i)
            }

        }

    }
}