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

    //テキストフィールドのUI状態やローディング状態を管理する状態管理
    private val _textFieldUiState = MutableStateFlow(PostScheduleUiState())
    val textFieldUiState = _textFieldUiState.asStateFlow()

    //テキストフィールドのエラー状態を管理する状態管理
    private val _textFieldErrorUiState = MutableStateFlow(TextFieldError())
    val textFieldErrorUiState = _textFieldErrorUiState.asStateFlow()

    //テキストフィールドのエラーメッセージ状態を管理する状態管理
    private val _textFieldErrorMsgUiState = MutableStateFlow(TextFieldErrorMsg())
    val textFieldErrorMsgUiState = _textFieldErrorMsgUiState.asStateFlow()

    //会社名のテキストを管理するメソッド
    fun onCompanyNameValueChange(completedText: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                companyName = completedText
            )
        )
    }

    //インターンシップ名のテキストを管理するメソッド
    fun onInternshipNameValueChange(completedText: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                internshipName = completedText
            )
        )
    }

    //日付のテキストを管理するメソッド
    fun onDateValueChange(completedDate: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                date = completedDate
            )
        )
    }

    //選考のテキストを管理するメソッド
    fun onRouteValueChange(chosenItem: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                route = chosenItem
            ),
            routeExpanded = false
        )
    }

    //選考状況のテキストを管理するメソッド
    fun onStatusValueChange(chosenItem: String) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                routeStatus = chosenItem
            ),
            statusExpanded = false
        )
    }

    //選考が選ばれたら、ドロップダウンリストを消去するメソッド
    fun onRouteExpandedChange(routeExpanded: Boolean) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            routeExpanded = !routeExpanded
        )
    }

    //ドロップダウンリストの外面をタップしたら、ドロップダウンリストを消去するメソッド
    fun onRouteDismissRequest() {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            routeExpanded = false
        )
    }

    //選考状況が選ばれたら、ドロップダウンリストを消去するメソッド
    fun onStatusExpandedChange(statusExpanded: Boolean) {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            statusExpanded = !statusExpanded,
        )
    }

    //ドロップダウンリストの外面をタップしたら、ドロップダウンリストを消去するメソッド
    fun onStatusDismissRequest() {
        _textFieldUiState.value = _textFieldUiState.value.copy(
            statusExpanded = false
        )
    }

    //投稿するボタンが押されたら、スケジュールを登録するメソッド
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

data class TextFieldError(
    val isCompanyNameValid: Boolean = false,
    val isInternshipName: Boolean = false,
    val isDateValid: Boolean = false,
    val isRouteValid: Boolean = false,
    val isRouteStatusValid: Boolean = false
)

data class TextFieldErrorMsg(
    val companyNameError: String = "",
    val internshipNameError: String = "",
    val dateError: String = "",
    val routeError: String= "",
    val routeStatusError: String= ""
)
