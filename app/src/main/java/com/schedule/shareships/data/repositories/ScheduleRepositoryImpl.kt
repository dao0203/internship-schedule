package com.schedule.shareships.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.schedule.shareships.data.models.ScheduleModel
import com.schedule.shareships.data.pagingsources.SchedulePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore,
) : ScheduleRepository {

    private val scheduleRef = firestore.collection("schedule")
    private val scheduleQuery = scheduleRef.orderBy("date", Query.Direction.ASCENDING)

    override fun getSchedule(): SchedulePagingSource {
        return SchedulePagingSource(scheduleQuery)
    }

    override suspend fun insertSchedule(scheduleModel: ScheduleModel) {
        withContext(Dispatchers.IO) {
            scheduleRef.add(scheduleModel)
        }
    }
}
