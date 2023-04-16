package com.schedule.eachinternshipschedule.model

data class ScheduleErrorMsg(
    val companyNameError: String,
    val internshipNameError: String,
    val dateError: String,
    val routeError: String,
    val routeStatusError: String
)
