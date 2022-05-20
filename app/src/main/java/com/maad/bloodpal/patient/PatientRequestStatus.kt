package com.maad.bloodpal.patient

class PatientRequestStatus(
    val patientRequest: PatientRequest = PatientRequest(),
    val hospitalId: String = "",
    val status: String = "",
)
