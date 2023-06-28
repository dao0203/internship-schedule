package com.schedule.shareships.data.repositories

import com.schedule.shareships.data.pagingsources.SchedulePagingSource
import com.schedule.shareships.domain.objects.Schedule

interface ScheduleRepository {
    suspend fun insertSchedule(schedule: Schedule)
    fun getSchedule(): SchedulePagingSource
}
