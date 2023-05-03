package com.schedule.shareships.model

data class Schedule(
    val companyName: String,
    val internshipName: String,
    val date: String,
    val route: String,
    val routeStatus: String
) {
    constructor() : this("", "", "", "", "")
}
