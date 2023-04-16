package com.schedule.eachinternshipschedule.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.schedule.eachinternshipschedule.model.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class DefaultFirestoreRepository(firestore: FirebaseFirestore) : FirestoreRepository {

    private val scheduleRef = firestore.collection("schedule")

    override suspend fun getSchedule(): Flow<List<Schedule>> = flow {
        val scheduleList = mutableListOf<Schedule>()
        val snapshot = scheduleRef.get().await()
        for(document in snapshot.documents){
            val schedule = Schedule(
                companyName = document.getString("companyName") ?: "NoData",
                internshipName = document.getString("internshipName") ?: "NoData",
                date = document.getString("date") ?: "NoData",
                route = document.getString("route") ?: "NoData",
                routeStatus = document.getString("routeStatus") ?: "NoData"
            )
            scheduleList.add(schedule)
        }
        emit(scheduleList)
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        scheduleRef.add(schedule)
    }
}