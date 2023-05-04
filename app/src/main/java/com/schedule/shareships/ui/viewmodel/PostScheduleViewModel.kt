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

    private val _isPostButtonEnabled = MutableStateFlow(false)
    val isPostButtonEnabled = _isPostButtonEnabled.asStateFlow()

    //会社名のテキストを管理するメソッド
    fun onCompanyNameValueChange(completedText: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isCompanyNameInvalid = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                companyName = completedText
            )
        )

        //もし、空白だったらエラーを出力する
        if (textFieldUiState.value.schedule.companyName == Constants.BLANK_SPACE) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isCompanyNameInvalid = true
            )
        }
        //投稿ボタンを有効にするかどうかを判断する
        _isPostButtonEnabled.value = validateForm()
    }

    //インターンシップ名のテキストを管理するメソッド
    fun onInternshipNameValueChange(completedText: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isInternshipNameInvalid = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                internshipName = completedText
            )
        )
        //もし、空白だったらエラーを出力する
        if (textFieldUiState.value.schedule.internshipName == Constants.BLANK_SPACE) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isInternshipNameInvalid = true
            )
        }
        //投稿ボタンを有効にするかどうかを判断する
        _isPostButtonEnabled.value = validateForm()
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
            isDateInvalid = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                date = "$pickedYear/$pickedMonth/$pickedDayOfMonth"
            )
        )
        //もし、空白だったらエラーを出力する
        if (textFieldUiState.value.schedule.date == Constants.BLANK_SPACE) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isDateInvalid = true
            )
        }
        //投稿ボタンを有効にするかどうかを判断する
        _isPostButtonEnabled.value = validateForm()
    }

    //選考のテキストを管理するメソッド
    fun onRouteValueChange(chosenItem: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isRouteInvalid = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                route = chosenItem
            ),
            routeExpanded = false
        )

        //もし、初期状態が入力されたらエラーを表示する
        if (
            textFieldUiState.value.schedule.route ==
            Constants.ROUTE_ITEMS[Constants.DROPDOWN_MENU_OF_INITIAL_STATE_INDEX]
        ) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isRouteInvalid = true
            )
        }
        //投稿ボタンを有効にするかどうかを判断する
        _isPostButtonEnabled.value = validateForm()
    }

    //選考状況のテキストを管理するメソッド
    fun onStatusValueChange(chosenItem: String) {

        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isRouteStatusInvalid = false
        )

        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                routeStatus = chosenItem
            ),
            statusExpanded = false
        )

        //初期状態が選ばれたときはエラーを表示する
        if (
            textFieldUiState.value.schedule.routeStatus ==
            Constants.STATUS_ITEMS[Constants.DROPDOWN_MENU_OF_INITIAL_STATE_INDEX]
        ) {
            //エラーを表示する
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isRouteStatusInvalid = true
            )
        }
        //投稿ボタンを有効にするかどうかを判断する
        _isPostButtonEnabled.value = validateForm()
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
        if (!textFieldErrorUiState.value.isCompanyNameInvalid) {
            viewModelScope.launch {
                _textFieldUiState.value = _textFieldUiState.value.copy(
                    isLoading = true
                )
                firestoreRepository.insertSchedule(schedule)
                _onPressedPostButtonEvent.emit(true)
            }
        }

    }

    private fun validateForm(): Boolean {
        return textFieldUiState.value.schedule.companyName != Constants.BLANK_SPACE
                &&
                textFieldUiState.value.schedule.internshipName != Constants.BLANK_SPACE
                &&
                textFieldUiState.value.schedule.date != Constants.BLANK_SPACE
                &&
                textFieldUiState.value.schedule.route !=
                Constants.ROUTE_ITEMS[Constants.DROPDOWN_MENU_OF_INITIAL_STATE_INDEX]
                &&
                textFieldUiState.value.schedule.routeStatus !=
                Constants.STATUS_ITEMS[Constants.DROPDOWN_MENU_OF_INITIAL_STATE_INDEX]
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
    val isCompanyNameInvalid: Boolean = false,
    val isInternshipNameInvalid: Boolean = false,
    val isDateInvalid: Boolean = false,
    val isRouteInvalid: Boolean = false,
    val isRouteStatusInvalid: Boolean = false
)
