package com.maad.bloodpal.hospital

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Hospital(
    val id: String = "",
    val userType: String = "",
    val website: String = "",
    val name: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val businessHours: String = "",
    val businessDays: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val logo: String = "",
    val totalRating: Double = 5.0,
    val ratingCounter: Int = 1,
    val finalRating: Double = 5.0,
    val distance: Double = 0.0
) : Parcelable, Comparable<Hospital> {

    override fun compareTo(other: Hospital) = distance.compareTo(other.distance)

}