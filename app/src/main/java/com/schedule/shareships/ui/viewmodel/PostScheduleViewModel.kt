package com.schedule.shareships.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schedule.shareships.data.repository.FirestoreRepository
import com.schedule.shareships.model.Schedule
import com.schedule.shareships.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    //投稿ボタンが押されたら、リスト画面に戻るイベント
    private val _onPressedPostButtonEvent = MutableSharedFlow<Boolean>()
    val onPressedPostButtonEvent = _onPressedPostButtonEvent.asSharedFlow()

    //会社名のテキストを管理するメソッド
    fun onCompanyNameValueChange(completedText: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isCompanyNameError = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                companyName = completedText
            )
        )
    }

    //インターンシップ名のテキストを管理するメソッド
    fun onInternshipNameValueChange(completedText: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isInternshipNameError = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                internshipName = completedText
            )
        )
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
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isDateError = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                date = "$pickedYear/$pickedMonth/$pickedDayOfMonth"
            )
        )
    }

    //選考のテキストを管理するメソッド
    fun onRouteValueChange(chosenItem: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isRouteError = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                route = chosenItem
            ),
            routeExpanded = false
        )
    }

    //選考状況のテキストを管理するメソッド
    fun onStatusValueChange(chosenItem: String) {

        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isRouteStatusError = false
        )

        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                routeStatus = chosenItem
            ),
            statusExpanded = false,
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

    //投稿するボタンが押されたときの処理
    fun onPressedPostButton(schedule: Schedule) {
        //入力されたテキストをデータレイヤに送る
        viewModelScope.launch {
            _textFieldUiState.value = _textFieldUiState.value.copy(
                isLoading = true
            )
            firestoreRepository.insertSchedule(schedule)
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
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isCompanyNameError = true
            )
        }
        //インターンシップ名のバリデーション
        if (internshipName.isEmpty()) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isInternshipNameError = true
            )
        }
        //日付のバリデーション
        if (date.isEmpty()) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isDateError = true
            )
        }
        //選考のバリデーション
        if (route == Constants.ROUTE_ITEMS[Constants.DROPDOWN_MENU_OF_INITIAL_STATE_INDEX]) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isRouteError = true
            )
        }
        //選考状況のバリデーション
        if (routeStatus == Constants.STATUS_ITEMS[Constants.DROPDOWN_MENU_OF_INITIAL_STATE_INDEX]) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isRouteStatusError = true
            )
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
        companyName = Constants.BLANK_SPACE,
        internshipName = Constants.BLANK_SPACE,
        date = Constants.BLANK_SPACE,
        route = Constants.ROUTE_ITEMS[Constants.DROPDOWN_MENU_OF_INITIAL_STATE_INDEX],
        routeStatus = Constants.STATUS_ITEMS[Constants.DROPDOWN_MENU_OF_INITIAL_STATE_INDEX]
    )
)

data class TextFieldError(
    val isCompanyNameError: Boolean = false,
    val isInternshipNameError: Boolean = false,
    val isDateError: Boolean = false,
    val isRouteError: Boolean = false,
    val isRouteStatusError: Boolean = false
)
