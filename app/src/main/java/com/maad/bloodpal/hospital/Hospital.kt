package com.maad.bloodpal.hospital

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Hospital(
    val id: String = "",
    val userType: String = "",
    val rating: Double = 0.0,
    val website: String = "",
    val name: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val businessHours: String = "",
    val businessDays: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val logo: String = ""
) : Parcelable