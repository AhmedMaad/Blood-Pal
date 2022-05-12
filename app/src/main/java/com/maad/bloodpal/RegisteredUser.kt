package com.maad.bloodpal

class RegisteredUser(
    val userId: String = "",
    val userType: String = "",
    picture: String = "",
    email: String = "",
    password: String = "",
    mobile: String = "",
    idNumber: String = "",
    gender: String = ""
) : User(picture, email, password, mobile, idNumber, gender)