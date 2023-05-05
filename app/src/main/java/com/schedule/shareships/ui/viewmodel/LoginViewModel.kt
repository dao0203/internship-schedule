package com.schedule.shareships.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginTextFieldErrorState = MutableStateFlow(LoginTextFieldErrorUiState())
    val loginTextFieldErrorState = _loginTextFieldErrorState.asStateFlow()

}

data class LoginTextFieldErrorUiState(
    val isMailAddressInValid: Boolean = false,
    val isPasswordInValid: Boolean = false
)