package com.schedule.eachinternshipschedule.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schedule.eachinternshipschedule.data.repository.FirestoreRepository
import com.schedule.eachinternshipschedule.model.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PostScheduleViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _textFieldUiState = MutableStateFlow(PostScheduleUiState())
    val textFieldUiState = _textFieldUiState.asStateFlow()

    fun onCompanyNameValueChange(completedText: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                companyName = completedText
            )
        )
    }

    fun onInternshipNameValueChange(completedText: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                internshipName = completedText
            )
        )
    }

    fun onDateValueChange(completedDate: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                date = completedDate
            )
        )
    }

    fun onRouteValueChange(chosenItem: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                route = chosenItem
            ),
            routeExpanded = false
        )
    }

    fun onStatusValueChange(chosenItem: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                routeStatus = chosenItem
            ),
            statusExpanded = false
        )
    }

    fun onRouteExpandedChange(routeExpanded: Boolean) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            routeExpanded = !routeExpanded
        )
    }

    fun onRouteDismissRequest() {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            routeExpanded = false
        )
    }

    fun onStatusExpandedChange(statusExpanded: Boolean) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            statusExpanded = !statusExpanded,
        )
    }

    fun onStatusDismissRequest() {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            statusExpanded = false
        )
    }

    fun insertSchedule(schedule: Schedule) {
        viewModelScope.launch {
            _textFieldUiState.value = _textFieldUiState.value.copy(
                isLoading = true
            )
            firestoreRepository.insertSchedule(schedule)
            _textFieldUiState.value = _textFieldUiState.value.copy(
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
