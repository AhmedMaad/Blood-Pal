package com.maad.bloodpal.hospital

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.bloodpal.R
import java.util.ArrayList

class PatientRequestsAdapter(
    val activity: PatientRequestsActivity,
    val patientAndUser: ArrayList<PatientAndUser>,
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<PatientRequestsAdapter.VH>() {

    interface ItemClickListener {
        fun onAcceptBtnClick(position: Int)
        fun onDenyBtnClick(position: Int)
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val profile: ImageView = view.findViewById(R.id.profile_iv)
        val healthStatus: TextView = view.findViewById(R.id.health_status_tv)
        val accept: TextView = view.findViewById(R.id.accept_tv)
        val deny: TextView = view.findViewById(R.id.deny_tv)

        init {
            accept.setOnClickListener { itemClickListener.onAcceptBtnClick(adapterPosition) }
            deny.setOnClickListener { itemClickListener.onDenyBtnClick(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(activity.layoutInflater.inflate(R.layout.patient_request_list_item, parent, false))


    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(activity).load(patientAndUser[position].user.picture).into(holder.profile)
        for (status in patientAndUser[position].patientRequest.healthStatus)
            holder.healthStatus.append("- $status")
    }

    override fun getItemCount() = patientAndUser.size

}
