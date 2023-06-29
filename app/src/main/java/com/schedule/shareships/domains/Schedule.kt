package com.schedule.shareships.domains


data class Schedule(
    var id: String = "",
    val companyName: String,
    val internshipName: String,
    val date: String,
    val route: String,
    val routeStatus: String,
    var createdAt: String = "",
    var updatedAt: String = ""
)
