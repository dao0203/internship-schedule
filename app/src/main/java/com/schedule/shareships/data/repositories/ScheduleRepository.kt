package com.schedule.shareships.data.repositories

import com.schedule.shareships.data.source.SchedulePagingSource
import com.schedule.shareships.data.model.Schedule

interface ScheduleRepository {
    suspend fun insertSchedule(schedule: Schedule)
    fun getSchedule(): SchedulePagingSource
}
