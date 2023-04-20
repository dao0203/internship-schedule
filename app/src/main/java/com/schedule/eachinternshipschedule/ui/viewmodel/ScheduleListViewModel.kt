package com.schedule.eachinternshipschedule.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    val items: Flow<PagingData<Schedule>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { repository.getSchedule() }
    ).flow.cachedIn(viewModelScope)
}
