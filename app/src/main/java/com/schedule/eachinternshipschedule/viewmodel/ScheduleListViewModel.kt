package com.schedule.eachinternshipschedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleListViewModel(private val repository: FirestoreRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleListUiState.Loading)
    val uiState = _uiState.asStateFlow()
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

sealed interface ScheduleListUiState {
    object Loading : ScheduleListUiState
    data class Success(val scheduleList: List<Schedule>) : ScheduleListUiState
    data class Error(val e: String) : ScheduleListUiState
}
