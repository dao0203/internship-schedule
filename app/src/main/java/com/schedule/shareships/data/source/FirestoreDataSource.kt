package com.schedule.shareships.data.source

import com.google.firebase.firestore.Query
import com.schedule.shareships.data.model.Schedule

interface FirestoreDataSource {
    fun getSchedulesQuery(): Query
    suspend fun insertSchedule(schedule: Schedule)

    //TODO：返り値は現状String型だが、後々変更する可能性あり
    suspend fun getUserData(email: String): String

    suspend fun getFollowedUsersQuery(email: String): Query

    suspend fun getFollowingUsersQuery(email: String): Query

    suspend fun getUsersSchedulesQuery(email: String): Query

    suspend fun followUser(email: String, followedUserEmail: String)
}
