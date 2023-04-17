package com.schedule.eachinternshipschedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow

class ScheduleListViewModel(private val repository: FirestoreRepository) : ViewModel() {
    val items: Flow<PagingData<Schedule>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { repository.getSchedule() }
    ).flow.cachedIn(viewModelScope)
}

class ScheduleListViewModelFactory(private val repository: FirestoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
