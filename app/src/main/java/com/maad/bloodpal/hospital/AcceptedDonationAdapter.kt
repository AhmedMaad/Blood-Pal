package com.maad.bloodpal.hospital

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.bloodpal.R

class AcceptedDonationAdapter(val activity: Activity, val requests: ArrayList<AcceptedDonation>) :
    RecyclerView.Adapter<AcceptedDonationAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val profilePic: ImageView = view.findViewById(R.id.picture_iv)
        val emailTV: TextView = view.findViewById(R.id.email_tv)
        val phoneTV: TextView = view.findViewById(R.id.phone_tv)
        val bloodTypeTV: TextView = view.findViewById(R.id.blood_type_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(activity.layoutInflater.inflate(R.layout.accepted_donation_list_item, parent, false))

    override fun onBindViewHolder(holder: AcceptedDonationAdapter.VH, position: Int) {
        Glide.with(activity).load(requests[position].user.picture).into(holder.profilePic)
        holder.emailTV.text = requests[position].user.email
        holder.phoneTV.text = requests[position].user.mobile
        holder.bloodTypeTV.text = requests[position].bloodType
    }

    override fun getItemCount() = requests.size
}