package com.schedule.shareships.data.repositories

import com.schedule.shareships.data.models.ScheduleModel
import com.schedule.shareships.data.pagingsources.SchedulePagingSource

interface ScheduleRepository {
    suspend fun insertSchedule(scheduleModel: ScheduleModel)
    fun getSchedule(): SchedulePagingSource
}
