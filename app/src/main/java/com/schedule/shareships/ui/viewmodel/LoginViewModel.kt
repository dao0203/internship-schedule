package com.schedule.shareships.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.schedule.shareships.model.LoginData
import com.schedule.shareships.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginTextFieldErrorUiState = MutableStateFlow(LoginTextFieldErrorUiState())
    val loginTextFieldErrorUiState = _loginTextFieldErrorUiState.asStateFlow()
    private val _loginTextFieldTextUiState = MutableStateFlow(LoginData(
        email = Constants.BLANK_SPACE,
        password = Constants.BLANK_SPACE
    ))
}

data class LoginTextFieldErrorUiState(
    val isMailAddressInValid: Boolean = false,
    val isPasswordInValid: Boolean = false
)