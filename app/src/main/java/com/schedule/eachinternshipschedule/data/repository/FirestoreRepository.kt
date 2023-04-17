package com.schedule.eachinternshipschedule.data.repository

import com.schedule.eachinternshipschedule.model.Schedule
import kotlinx.coroutines.flow.Flow


interface FirestoreRepository {
    suspend fun insertSchedule(schedule: Schedule)
    suspend fun getSchedule() : Flow<List<Schedule>>
}