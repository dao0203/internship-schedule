package com.schedule.eachinternshipschedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.model.ScheduleErrorMsg
import com.schedule.eachinternshipschedule.model.ScheduleValid
import com.schedule.eachinternshipschedule.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PostScheduleViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PostScheduleUiState())
    fun insertSchedule(schedule: Schedule) {
        viewModelScope.launch {
            firestoreRepository.insertSchedule(schedule)
        }
    }
}

class PostScheduleViewModelFactory(private val repository: FirestoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostScheduleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class PostScheduleUiState(
    val isLoading: Boolean = false,
    val routeExpanded : Boolean = false,
    val statusExpanded : Boolean = false,
    val schedule: Schedule = Schedule(
        companyName = "",
        internshipName = "",
        date = "",
        route = "",
        routeStatus = ""
    ),
    val scheduleErrorMsg: ScheduleErrorMsg = ScheduleErrorMsg(
        companyNameError = "",
        internshipNameError = "",
        dateError = "",
        routeError = "",
        routeStatusError = "",
    ),
    val scheduleValid: ScheduleValid = ScheduleValid(
        isCompanyNameValid = false,
        isInternshipName = false,
        isDateValid = false,
        isRouteStatusValid = false,
        isRouteValid = false,
    )
)
