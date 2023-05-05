package com.schedule.shareships.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginTextFieldErrorState = MutableStateFlow(LoginTextFieldErrorState())
    val loginTextFieldErrorState = _loginTextFieldErrorState.asStateFlow()

}

data class LoginTextFieldErrorState(
    val isMailAddressInValid: Boolean = false,
    val isPasswordInValid: Boolean = false
)