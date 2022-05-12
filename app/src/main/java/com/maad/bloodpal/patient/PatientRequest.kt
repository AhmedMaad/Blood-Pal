package com.maad.bloodpal.patient
//send lat, lng, health status and user id, request od

class PatientRequest(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val healthStatus: ArrayList<String> = arrayListOf(),
    val userId: String = "",
    val requestId: String = ""
)