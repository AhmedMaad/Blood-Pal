package com.maad.bloodpal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class User(
    val picture: String = "",
    val email: String = "",
    val password: String = "",
    val mobile: String = "",
    val idNumber: String = "",
    val gender: String = "",
) : Parcelable