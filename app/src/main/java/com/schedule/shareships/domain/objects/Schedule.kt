package com.schedule.shareships.domain.objects

data class Schedule(
    val id: String,
    val companyName: String,
    val internshipName: String,
    val date: String,
    val route: String,
    val routeStatus: String,
    val createdAt: String,
    val updatedAt: String
)