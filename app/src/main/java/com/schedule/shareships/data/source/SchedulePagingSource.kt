package com.schedule.shareships.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.schedule.shareships.utils.Constants
import com.schedule.shareships.data.model.Schedule
import kotlinx.coroutines.tasks.await

class SchedulePagingSource(private val query: Query) : PagingSource<QuerySnapshot, Schedule>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Schedule>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Schedule> {
        return try {

            val currentPage = params.key ?: query
                .limit(Constants.PAGE_SIZE.toLong())
                .get()
                .await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = query
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
