package com.schedule.eachinternshipschedule.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.schedule.eachinternshipschedule.data.paging_source.SchedulePagingSource
import com.schedule.eachinternshipschedule.model.Schedule

class DefaultFirestoreRepository(val firestore: FirebaseFirestore) : FirestoreRepository {

    private val scheduleRef = firestore.collection("schedule")

    override fun getSchedule(): SchedulePagingSource {
        return SchedulePagingSource(firestore)
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        scheduleRef.add(schedule)
    }
}