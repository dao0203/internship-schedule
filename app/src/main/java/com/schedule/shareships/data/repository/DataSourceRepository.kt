package com.schedule.shareships.data.repository

import com.schedule.shareships.data.source.SchedulePagingSource
import com.schedule.shareships.model.Schedule

interface DataSourceRepository {
    suspend fun insertSchedule(schedule: Schedule)
    fun getSchedule(): SchedulePagingSource
}
