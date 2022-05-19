package com.maad.bloodpal.donor

import com.maad.bloodpal.hospital.BloodRequest

class DonorRequestStatus(
    val bloodRequest: BloodRequest = BloodRequest(),
    val userId: String = "",
    val status: String = "",
)
