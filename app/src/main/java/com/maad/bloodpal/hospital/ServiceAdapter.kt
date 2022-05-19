package com.maad.bloodpal.hospital

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.maad.bloodpal.R

class ServiceAdapter(val activity: Activity, val services: ArrayList<String>) :
    RecyclerView.Adapter<ServiceAdapter.ServiceVH>() {

    class ServiceVH(view: View) : RecyclerView.ViewHolder(view) {
        val serviceTV: TextView = view.findViewById(R.id.services_tv)
        val parent: CardView = view.findViewById(R.id.parent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceVH {
        val view =
            activity.layoutInflater.inflate(R.layout.hospital_service_list_item, parent, false)
        return ServiceVH(view)
    }

    override fun onBindViewHolder(holder: ServiceVH, position: Int) {
        holder.serviceTV.text = services[position]

        holder.parent.setOnClickListener {
            val destinationActivity = when (position) {
                0 -> FillPatientProfileActivity::class.java
                1 -> FillDonorProfileActivity::class.java
                2 -> PatientRequestsActivity::class.java
                3 -> LateDonorsActivity::class.java
                4 -> RequestDonationActivity::class.java
                else -> AcceptedDonationRequestsActivity::class.java
            }
            activity.startActivity(Intent(activity, destinationActivity))
        }

    }

    override fun getItemCount() = services.size

}