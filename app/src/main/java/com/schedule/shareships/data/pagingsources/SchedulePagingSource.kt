package com.schedule.shareships.data.pagingsources

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.schedule.shareships.domains.Schedule
import kotlinx.coroutines.tasks.await
import java.util.Locale

class SchedulePagingSource(private val query: Query) : PagingSource<QuerySnapshot, Schedule>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Schedule>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Schedule> {
        return try {

            val currentPage = params.key ?: query
                .limit(20.toLong())
                .get()
                .await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = query
                .limit(20.toLong())
                .startAfter(lastVisibleProduct)
                .get()
                .await()
            LoadResult.Page(
                data = currentPage.documents.map {
                    val date = it.getTimestamp("date")!!.toDate()
                    val createdAt = it.getTimestamp("createdAt")!!.toDate()
                    val updatedAt = it.getTimestamp("updatedAt")!!.toDate()

                    val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                    val dateString = formatter.format(date)
                    val createdAtString = formatter.format(createdAt)
                    val updatedAtString = formatter.format(updatedAt)
                    Schedule(
                        id = it.id,
                        companyName = it.getString("companyName") ?: "",
                        internshipName = it.getString("internshipName") ?: "",
                        date = dateString,
                        route = it.getString("route") ?: "",
                        routeStatus = it.getString("routeStatus") ?: "",
                        createdAt = createdAtString,
                        updatedAt = updatedAtString
                    )
                },
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            Log.e("SchedulePagingSource", "load: $e")
            LoadResult.Error(e)
        }
    }

}
