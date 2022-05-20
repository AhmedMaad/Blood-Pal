package com.maad.bloodpal.hospital

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.maad.bloodpal.R

class LateDonorAdapter(val activity: Activity, val lateDonors: ArrayList<LateDonor>) :
    RecyclerView.Adapter<LateDonorAdapter.DonorVH>() {

    class DonorVH(view: View) : RecyclerView.ViewHolder(view) {
            val parent: CardView = view.findViewById(R.id.parent)
        val email: TextView = view.findViewById(R.id.email_tv)
        val date: TextView = view.findViewById(R.id.date_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DonorVH(activity.layoutInflater.inflate(R.layout.late_donors_list_item, parent, false))

    override fun onBindViewHolder(holder: LateDonorAdapter.DonorVH, position: Int) {
        holder.email.text = lateDonors[position].email
        holder.date.text = lateDonors[position].lastDonation
        holder.parent.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND, "mailto:".toUri())
            i.type = "*/*"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(lateDonors[position].email))
            i.putExtra(Intent.EXTRA_SUBJECT, "Donation Reminder")
            activity.startActivity(i)
        }
    }

    override fun getItemCount() = lateDonors.size
}