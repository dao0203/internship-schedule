package com.schedule.shareships.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schedule.shareships.domains.Schedule
import com.schedule.shareships.usecases.PostScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PostScheduleViewModel @Inject constructor(
    private val postScheduleUseCase: PostScheduleUseCase
) : ViewModel() {

    //テキストフィールドのUI状態やローディング状態を管理する状態管理
    private val _textFieldUiState = MutableStateFlow(PostScheduleUiState())
    val textFieldUiState = _textFieldUiState.asStateFlow()

    //テキストフィールドのエラー状態を管理する状態管理
    private val _textFieldErrorUiState = MutableStateFlow(TextFieldError())
    val textFieldErrorUiState = _textFieldErrorUiState.asStateFlow()

    //投稿ボタンが押されたら、リスト画面に戻るイベント
    private val _onPressedPostButtonEvent = MutableSharedFlow<Boolean>()
    val onPressedPostButtonEvent = _onPressedPostButtonEvent.asSharedFlow()

    //会社名のテキストを管理するメソッド
    fun onCompanyNameValueChange(completedText: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.update { it.copy(isCompanyNameError = false) }
        //入力されたテキストに更新する
        _textFieldUiState.update { it.copy(schedule = it.schedule.copy(companyName = completedText)) }
    }

    //インターンシップ名のテキストを管理するメソッド
    fun onInternshipNameValueChange(completedText: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.update { it.copy(isInternshipNameError = false) }
        //入力されたテキストに更新する
        _textFieldUiState.update { it.copy(schedule = it.schedule.copy(internshipName = completedText)) }
    }

    //日付のテキストを管理するメソッド
    fun onDateValueChange(year: Int, month: Int, dayOfMonth: Int) {
        val pickedYear = year.toString()
        val pickedMonth = if (month < 10) {
            "0$month"
        } else {
            month.toString()
        }
        val pickedDayOfMonth = if (dayOfMonth < 10) {
            "0$dayOfMonth"
        } else {
            dayOfMonth.toString()
        }
        //エラーを非表示にする
        _textFieldErrorUiState.update { it.copy(isDateError = false) }
        //入力された日付に更新する
        _textFieldUiState.update { it.copy(schedule = it.schedule.copy(date = "$pickedYear/$pickedMonth/$pickedDayOfMonth")) }
    }

    //選考のテキストを管理するメソッド
    fun onRouteValueChange(chosenItem: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.update { it.copy(isRouteError = false) }
        //入力された選考に更新する
        _textFieldUiState.update {
            it.copy(
                routeExpanded = false,
                schedule = it.schedule.copy(route = chosenItem),
            )
        }
    }

    //選考状況のテキストを管理するメソッド
    fun onStatusValueChange(chosenItem: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.update { it.copy(isRouteStatusError = false) }
        //入力された選考状況に更新する
        _textFieldUiState.update {
            it.copy(
                statusExpanded = false,
                schedule = it.schedule.copy(routeStatus = chosenItem),
            )
        }
    }

    //選考が選ばれたら、ドロップダウンリストを消去するメソッド
    fun onRouteExpandedChange(routeExpanded: Boolean) {
        _textFieldUiState.update { it.copy(routeExpanded = !routeExpanded) }
    }


    //ドロップダウンリストの外面をタップしたら、ドロップダウンリストを消去するメソッド
    fun onRouteDismissRequest() {
        _textFieldUiState.update { it.copy(routeExpanded = false) }
    }

    //選考状況が選ばれたら、ドロップダウンリストを消去するメソッド
    fun onStatusExpandedChange(statusExpanded: Boolean) {
        _textFieldUiState.update { it.copy(statusExpanded = !statusExpanded) }
    }

    //ドロップダウンリストの外面をタップしたら、ドロップダウンリストを消去するメソッド
    fun onStatusDismissRequest() {
        _textFieldUiState.update { it.copy(statusExpanded = false) }
    }

    //投稿するボタンが押されたときの処理
    fun onPressedPostButton() {
        //入力されたテキストをデータレイヤに送る
        viewModelScope.launch {
            _textFieldUiState.update { it.copy(isLoading = true) }
            postScheduleUseCase(_textFieldUiState.value.schedule)
            _onPressedPostButtonEvent.emit(true)
        }
    }

    fun validateForm(): Boolean {
        val companyName = _textFieldUiState.value.schedule.companyName
        val internshipName = _textFieldUiState.value.schedule.internshipName
        val date = _textFieldUiState.value.schedule.date
        val route = _textFieldUiState.value.schedule.route
        val routeStatus = _textFieldUiState.value.schedule.routeStatus

        //会社名のバリデーション
        if (companyName.isEmpty()) {
            _textFieldErrorUiState.update { it.copy(isCompanyNameError = true) }
        }
        //インターンシップ名のバリデーション
        if (internshipName.isEmpty()) {
            _textFieldErrorUiState.update { it.copy(isInternshipNameError = true) }
        }
        //日付のバリデーション
        if (date.isEmpty()) {
            _textFieldErrorUiState.update { it.copy(isDateError = true) }
        }
        //選考のバリデーション
        if (route.contentEquals("選考を選択してください")) {
            _textFieldErrorUiState.update { it.copy(isRouteError = true) }
        }
        //選考状況のバリデーション
        if (routeStatus.contentEquals("選考状況を選択してください")) {
            _textFieldErrorUiState.update { it.copy(isRouteStatusError = true) }
        }
        //バリデーションを通過したらtrueを返す
        return _textFieldErrorUiState.value.run {
            !isCompanyNameError && !isInternshipNameError && !isDateError && !isRouteError && !isRouteStatusError
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
        route = "",
        routeStatus = ""
    )
)

data class TextFieldError(
    val isCompanyNameError: Boolean = false,
    val isInternshipNameError: Boolean = false,
    val isDateError: Boolean = false,
    val isRouteError: Boolean = false,
    val isRouteStatusError: Boolean = false
)
