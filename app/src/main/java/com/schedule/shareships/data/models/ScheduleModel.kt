package com.schedule.shareships.data.models

import com.google.firebase.Timestamp

data class ScheduleModel(
    val companyName: String,
    val internshipName: String,
    val date: Timestamp,
    val route: String,
    val routeStatus: String,
    val createdAt: Timestamp,
    val updatedAt: Timestamp
)
