package com.maad.bloodpal.donor

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.bloodpal.R
import org.w3c.dom.Text

class BloodRequestsAdapter(
    val activity: Activity,
    val bloodAndHospitals: ArrayList<BloodAndHospital>
) : RecyclerView.Adapter<BloodRequestsAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val parent: CardView = view.findViewById(R.id.parent)
        val rating: TextView = view.findViewById(R.id.rating_tv)
        val profile: ImageView = view.findViewById(R.id.profile_iv)
        val name: TextView = view.findViewById(R.id.name_tv)
        val bloodType: TextView = view.findViewById(R.id.blood_type_value_tv)
        val accept: TextView = view.findViewById(R.id.accept_tv)
        val deny: TextView = view.findViewById(R.id.deny_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(activity.layoutInflater.inflate(R.layout.blood_request_list_item, parent, false))


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.rating.text = "%.2f".format(bloodAndHospitals[position].hospital.finalRating)
        Glide.with(activity).load(bloodAndHospitals[position].hospital.logo).into(holder.profile)
        holder.name.text = bloodAndHospitals[position].hospital.name
        holder.bloodType.text = bloodAndHospitals[position].bloodRequest.bloodType
    }

    override fun getItemCount() = bloodAndHospitals.size
}