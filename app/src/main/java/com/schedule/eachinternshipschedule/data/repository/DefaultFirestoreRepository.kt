package com.schedule.eachinternshipschedule.data.repository

import com.schedule.eachinternshipschedule.data.FirestoreDataSource
import com.schedule.eachinternshipschedule.data.SchedulePagingSource
import com.schedule.eachinternshipschedule.model.Schedule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultFirestoreRepository @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource
) : FirestoreRepository {

    override fun getSchedule(): SchedulePagingSource {
        return SchedulePagingSource(firestoreDataSource.getScheduleQuery())
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        firestoreDataSource.insertSchedule(schedule)
    }
}
