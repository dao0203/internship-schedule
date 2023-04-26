package com.schedule.eachinternshipschedule.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schedule.eachinternshipschedule.data.repository.FirestoreRepository
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.model.ScheduleErrorMsg
import com.schedule.eachinternshipschedule.model.ScheduleValid
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PostScheduleViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostScheduleUiState())
    val uiState = _uiState.asStateFlow()

    fun onCompanyNameValueChange(completedText: String) {
        _uiState.value = _uiState.value.copy(
            schedule = _uiState.value.schedule.copy(
                companyName = completedText
            )
        )
    }

    fun onInternshipNameValueChange(completedText: String) {
        _uiState.value = _uiState.value.copy(
            schedule = _uiState.value.schedule.copy(
                internshipName = completedText
            )
        )
    }

    fun onDateValueChange(completedDate: String) {
        _uiState.value = _uiState.value.copy(
            schedule = _uiState.value.schedule.copy(
                date = completedDate
            )
        )
    }

    fun onRouteValueChange(chosenItem: String) {
        _uiState.value = _uiState.value.copy(
            schedule = _uiState.value.schedule.copy(
                route = chosenItem
            ),
            routeExpanded = false
        )
    }

    fun onStatusValueChange(chosenItem: String) {
        _uiState.value = _uiState.value.copy(
            schedule = _uiState.value.schedule.copy(
                routeStatus = chosenItem
            ),
            statusExpanded = false
        )
    }

    fun onRouteExpandedChange(routeExpanded: Boolean) {
        _uiState.value = _uiState.value.copy(
            routeExpanded = !routeExpanded
        )
    }

    fun onRouteDismissRequest() {
        _uiState.value = _uiState.value.copy(
            routeExpanded = false
        )
    }

    fun onStatusExpandedChange(statusExpanded: Boolean) {
        _uiState.value = _uiState.value.copy(
            statusExpanded = !statusExpanded,
        )
    }

    fun onStatusDismissRequest() {
        _uiState.value = _uiState.value.copy(
            statusExpanded = false
        )
    }

    fun insertSchedule(schedule: Schedule) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            firestoreRepository.insertSchedule(schedule)
            _uiState.value = _uiState.value.copy(
                isLoading = false
            )
        }
    }
}

data class PostScheduleUiState(
    val isLoading: Boolean = false,
    val routeExpanded: Boolean = false,
    val statusExpanded: Boolean = false,
    val schedule: Schedule = Schedule(
        companyName = "",
        internshipName = "",
        date = "",
        route = "選考を選択してください",
        routeStatus = "選考状況を選択してください"
    )
)

data class ScheduleValid(
    val isCompanyNameValid: Boolean = false,
    val isInternshipName: Boolean = false,
    val isDateValid: Boolean = false,
    val isRouteValid: Boolean = false,
    val isRouteStatusValid: Boolean = false
)

data class ScheduleErrorMsg(
    val companyNameError: String = "",
    val internshipNameError: String = "",
    val dateError: String = "",
    val routeError: String= "",
    val routeStatusError: String= ""
)
