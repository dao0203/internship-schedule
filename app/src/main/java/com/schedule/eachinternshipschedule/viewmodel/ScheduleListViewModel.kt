package com.schedule.eachinternshipschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.repository.DefaultFirestoreRepository

class ScheduleListViewModel(private val repository: DefaultFirestoreRepository) : ViewModel() {

}

sealed interface ScheduleListUiState {
    object Loading : ScheduleListUiState
    data class Success(val scheduleList: List<Schedule>) : ScheduleListUiState
    object Error : ScheduleListUiState
}