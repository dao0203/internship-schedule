package com.schedule.shareships.usecases

import android.icu.text.SimpleDateFormat
import com.google.firebase.Timestamp
import com.schedule.shareships.data.models.ScheduleModel
import com.schedule.shareships.data.repositories.ScheduleRepository
import com.schedule.shareships.domains.Schedule
import java.util.Locale

class PostScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
) {
    private val formatter: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    suspend operator fun invoke(
        schedule: Schedule
    ) {
        val formattedDate = formatter.parse(schedule.date)
        val scheduleModel = ScheduleModel.fromPostScheduleData(
            companyName = schedule.companyName,
            internshipName = schedule.internshipName,
            date = Timestamp(formattedDate),
            route = schedule.route,
            routeStatus = schedule.routeStatus,
            createdAt = Timestamp.now(),
            updatedAt = Timestamp.now()
        )
        scheduleRepository.insertSchedule(scheduleModel)
    }
}