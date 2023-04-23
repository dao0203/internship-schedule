package com.schedule.eachinternshipschedule.data.repository

import com.schedule.eachinternshipschedule.data.SchedulePagingSource
import com.schedule.eachinternshipschedule.model.Schedule

interface FirestoreRepository {
    suspend fun insertSchedule(schedule: Schedule)
    fun getSchedule(): SchedulePagingSource
}
