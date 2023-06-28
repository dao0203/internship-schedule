package com.schedule.shareships.data.repositories

import com.schedule.shareships.data.source.SchedulePagingSource
import com.schedule.shareships.domain.objects.Schedule

interface ScheduleRepository {
    suspend fun insertSchedule(schedule: Schedule)
    fun getSchedule(): SchedulePagingSource
}
