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
            isCompanyNameValid = false
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
                isCompanyNameValid = true
            )
        }
    }

    //インターンシップ名のテキストを管理するメソッド
    fun onInternshipNameValueChange(completedText: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isInternshipNameValid = false
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
                isInternshipNameValid = true
            )
        }
    }

    //日付のテキストを管理するメソッド
    fun onDateValueChange(completedDate: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isDateValid = false
        )

        //入力されたテキストに更新する
        _textFieldUiState.value = _textFieldUiState.value.copy(
            schedule = _textFieldUiState.value.schedule.copy(
                date = completedDate
            )
        )
        //もし、空白だったらエラーを出力する
        if (textFieldUiState.value.schedule.date == Constants.BLANK_SPACE) {
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isDateValid = true
            )
        }
    }

    //選考のテキストを管理するメソッド
    fun onRouteValueChange(chosenItem: String) {
        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isRouteValid = false
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
                isRouteValid = true
            )
        }
    }

    //選考状況のテキストを管理するメソッド
    fun onStatusValueChange(chosenItem: String) {

        //エラーを非表示にする
        _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
            isRouteStatusValid = false
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
            _textFieldErrorUiState.value = _textFieldErrorUiState.value.copy(
                isRouteStatusValid = true
            )
        }
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
        viewModelScope.launch {
            _textFieldUiState.value = _textFieldUiState.value.copy(
                isLoading = true
            )
            firestoreRepository.insertSchedule(schedule)
            _onPressedPostButtonEvent.emit(true)
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
    val isCompanyNameValid: Boolean = false,
    val isInternshipNameValid: Boolean = false,
    val isDateValid: Boolean = false,
    val isRouteValid: Boolean = false,
    val isRouteStatusValid: Boolean = false
)
