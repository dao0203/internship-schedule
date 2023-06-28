package com.schedule.shareships.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.schedule.shareships.utils.Constants
import com.schedule.shareships.data.model.Schedule
import com.schedule.shareships.data.repositories.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {
    val items: Flow<PagingData<Schedule>> = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { repository.getSchedule() }
    ).flow.cachedIn(viewModelScope)
}
