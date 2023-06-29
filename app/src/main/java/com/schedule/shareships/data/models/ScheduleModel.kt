package com.schedule.shareships.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class ScheduleModel(
    @DocumentId val id: String,
    val companyName: String,
    val internshipName: String,
    val date: Timestamp,
    val route: String,
    val routeStatus: String,
    val createdAt: Timestamp,
    val updatedAt: Timestamp
) {
    companion object {
        fun fromPostScheduleData(
            companyName: String,
            internshipName: String,
            date: Timestamp,
            route: String,
            routeStatus: String,
            createdAt: Timestamp,
            updatedAt: Timestamp
        ): ScheduleModel {
            return ScheduleModel(
                id = "",
                companyName = companyName,
                internshipName = internshipName,
                date = date,
                route = route,
                routeStatus = routeStatus,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
