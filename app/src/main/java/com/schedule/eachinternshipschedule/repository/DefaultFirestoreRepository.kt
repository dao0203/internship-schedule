package com.schedule.eachinternshipschedule.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.schedule.eachinternshipschedule.model.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultFirestoreRepository(private val firestore: FirebaseFirestore) : FirestoreRepository {

    private val scheduleRef = firestore.collection("schedule")

    override suspend fun getSchedule(): Flow<List<Schedule>> = flow {
        return@flow
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        scheduleRef.add(schedule)
    }
}