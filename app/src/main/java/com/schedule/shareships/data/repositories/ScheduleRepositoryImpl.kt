package com.schedule.shareships.data.repositories

import android.icu.text.SimpleDateFormat
import com.google.firebase.Timestamp
import com.schedule.shareships.data.models.ScheduleModel
import com.schedule.shareships.data.sources.FirestoreDataSource
import com.schedule.shareships.data.pagingsources.SchedulePagingSource
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
        //TODO: Convert Schedule to ScheduleModel
        firestoreDataSource.insertSchedule(
            ScheduleModel(
                companyName = schedule.companyName,
                internshipName = schedule.internshipName,
                date = Timestamp(SimpleDateFormat("yyyy/MM/dd").parse(schedule.date)),
                route = schedule.route,
                routeStatus = schedule.routeStatus,
                createdAt = Timestamp.now(),
                updatedAt = Timestamp.now(),
            )
        )
    }
}
