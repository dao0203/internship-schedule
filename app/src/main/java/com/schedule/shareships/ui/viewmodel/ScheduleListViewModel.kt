package com.schedule.shareships.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.schedule.shareships.utils.Constants
import com.schedule.shareships.model.Schedule
import com.schedule.shareships.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    val items: Flow<PagingData<Schedule>> = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { repository.getSchedule() }
    ).flow.cachedIn(viewModelScope)
}