package com.schedule.eachinternshipschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.repository.DefaultFirestoreRepository

class ScheduleListViewModel(private val repository: DefaultFirestoreRepository) : ViewModel() {

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
    object Error : ScheduleListUiState
}