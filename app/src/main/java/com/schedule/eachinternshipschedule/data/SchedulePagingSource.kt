package com.schedule.eachinternshipschedule.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.schedule.eachinternshipschedule.utils.Constants
import com.schedule.eachinternshipschedule.model.Schedule
import kotlinx.coroutines.tasks.await

class SchedulePagingSource(firestore: FirebaseFirestore) : PagingSource<QuerySnapshot, Schedule>() {
    private val scheduleRef = firestore.collection("schedule")

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Schedule>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Schedule> {
        return try {

            val currentPage = params.key ?: scheduleRef
                .orderBy("date", Query.Direction.ASCENDING)

                .limit(Constants.PAGE_SIZE.toLong())
                .get()
                .await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = scheduleRef
                .orderBy("date", Query.Direction.ASCENDING)

                .limit(Constants.PAGE_SIZE.toLong())
                .startAfter(lastVisibleProduct)
                .get()
                .await()
             LoadResult.Page(
                data = currentPage.toObjects(Schedule::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
