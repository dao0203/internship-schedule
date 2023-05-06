package com.schedule.shareships.data.source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.schedule.shareships.model.Schedule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreDataSource @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val scheduleRef = firestore.collection("schedule")

    fun getScheduleQuery() = scheduleRef.orderBy("date", Query.Direction.ASCENDING)

    fun insertSchedule(schedule: Schedule) {
        scheduleRef.add(schedule)
    }
}
