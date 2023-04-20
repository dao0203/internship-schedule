package com.schedule.eachinternshipschedule.viewmodel

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

//class PostScheduleViewModelFactory(private val repository: FirestoreRepository) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(PostScheduleViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return PostScheduleViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

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
