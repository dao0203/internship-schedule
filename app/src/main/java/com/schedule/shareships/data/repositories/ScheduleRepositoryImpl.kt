package com.schedule.shareships.data.repositories

import android.icu.text.SimpleDateFormat
import com.google.firebase.Timestamp
import com.schedule.shareships.data.models.ScheduleModel
import com.schedule.shareships.data.source.FirestoreDataSource
import com.schedule.shareships.data.source.SchedulePagingSource
import com.schedule.shareships.domain.objects.Schedule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepositoryImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource
) : ScheduleRepository {

    override fun getSchedule(): SchedulePagingSource {
        return SchedulePagingSource(firestoreDataSource.getSchedulesQuery())
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        firestoreDataSource.insertSchedule(schedule)
    }
}
