package com.schedule.eachinternshipschedule.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.schedule.eachinternshipschedule.data.paging_source.SchedulePagingSource
import com.schedule.eachinternshipschedule.model.Schedule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultFirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreRepository {

    private val scheduleRef = firestore.collection("schedule")

    override fun getSchedule(): SchedulePagingSource {
        return SchedulePagingSource(firestore)
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        scheduleRef.add(schedule)
    }
}