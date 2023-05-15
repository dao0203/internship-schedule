package com.schedule.shareships.data.source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.schedule.shareships.model.Schedule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreDataSourceImpl @Inject constructor(
    firestore: FirebaseFirestore
) : FirestoreDataSource{

    private val scheduleRef = firestore.collection("schedule")

    override fun getSchedulesQuery() = scheduleRef.orderBy("date", Query.Direction.ASCENDING)

    override suspend fun getUserData(email: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowedUsersQuery(email: String): Query {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowingUsersQuery(email: String): Query {
        TODO("Not yet implemented")
    }

    override suspend fun getUsersSchedulesQuery(email: String): Query {
        TODO("Not yet implemented")
    }

    override suspend fun followUser(email: String, followedUserEmail: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        scheduleRef.add(schedule)
    }
}
