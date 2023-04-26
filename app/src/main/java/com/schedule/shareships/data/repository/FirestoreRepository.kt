package com.schedule.shareships.data.repository

import com.schedule.shareships.data.SchedulePagingSource
import com.schedule.shareships.model.Schedule

interface FirestoreRepository {
    suspend fun insertSchedule(schedule: Schedule)
    fun getSchedule(): SchedulePagingSource
}
