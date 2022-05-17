package com.maad.bloodpal.donor

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.bloodpal.R
import com.maad.bloodpal.hospital.Hospital
import kotlin.math.roundToInt

class AvailableHospitalsAdapter(
    val activity: AvailableHospitalsActivity,
    val hospitals: ArrayList<Hospital>
) : RecyclerView.Adapter<AvailableHospitalsAdapter.HospitalsVH>() {
    class HospitalsVH(view: View) : RecyclerView.ViewHolder(view) {
        val parent: CardView = view.findViewById(R.id.parent)
        val rating: TextView = view.findViewById(R.id.rating_tv)
        val profile: ImageView = view.findViewById(R.id.profile_iv)
        val name: TextView = view.findViewById(R.id.name_tv)
        val website: TextView = view.findViewById(R.id.website_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalsVH {
        val view =
            activity.layoutInflater.inflate(R.layout.available_hospitals_list_item, parent, false)
        return HospitalsVH(view)
    }

    override fun onBindViewHolder(holder: HospitalsVH, position: Int) {

        holder.parent.setOnClickListener {
            val i = Intent(activity, DonorHospitalProfileActivity::class.java)
            i.putExtra("hospital", hospitals[position])
            activity.startActivity(i)
        }

        holder.rating.text = "%.2f".format(hospitals[position].finalRating)
        Glide.with(activity).load(hospitals[position].logo).into(holder.profile)
        holder.name.text = hospitals[position].name
        holder.website.text = hospitals[position].website

        holder.website.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, hospitals[position].website.toUri())
            activity.startActivity(i)
        }

    }

    override fun getItemCount() = hospitals.size

}
