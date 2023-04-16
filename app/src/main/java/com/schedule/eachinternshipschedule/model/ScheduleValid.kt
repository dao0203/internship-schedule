package com.schedule.eachinternshipschedule.model

data class ScheduleValid(
    val isCompanyNameValid: Boolean,
    val isInternshipName: Boolean,
    val isDateValid: Boolean,
    val isRouteValid: Boolean,
    val isRouteStatusValid: Boolean
)
