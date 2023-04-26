package com.schedule.shareships.data.repository

import com.schedule.shareships.data.FirestoreDataSource
import com.schedule.shareships.data.SchedulePagingSource
import com.schedule.shareships.model.Schedule
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
