package com.schedule.shareships.data.repository

import com.schedule.shareships.data.source.FirestoreDataSource
import com.schedule.shareships.data.source.SchedulePagingSource
import com.schedule.shareships.model.Schedule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceRepositoryImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource
) : DataSourceRepository {

    override fun getSchedule(): SchedulePagingSource {
        return SchedulePagingSource(firestoreDataSource.getScheduleQuery())
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        firestoreDataSource.insertSchedule(schedule)
    }
}
