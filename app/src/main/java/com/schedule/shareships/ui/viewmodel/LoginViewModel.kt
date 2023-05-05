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
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    fun onEmailValueChange(completedText: String) {
        _loginUiState.value = _loginUiState.value.copy(
            loginData = _loginUiState.value.loginData.copy(
                email = completedText
            ),
            isEmailInValid = false
        )
    }

    fun onPasswordValueChange(completedText: String) {
        _loginUiState.value = _loginUiState.value.copy(
            loginData = _loginUiState.value.loginData.copy(
                password = completedText
            ),
            isPasswordInValid = false
        )
    }

//    fun validateForm(): Boolean {
//        val email = loginUiState.value.loginData.email
//        val password = loginUiState.value.loginData.password
//        if (email == Constants.BLANK_SPACE) {
//            _loginUiState.value = _loginUiState.value.copy(
//                isEmailInValid = true,
//                EmailErrorText = "メールアドレスを入力してください"
//            )
//        }
//    }
}

data class LoginUiState(
    val loginData: LoginData = LoginData(
        email = Constants.BLANK_SPACE,
        password = Constants.BLANK_SPACE
    ),
    val isEmailInValid: Boolean = false,
    val isPasswordInValid: Boolean = false,
    val isLoginButtonEnabled: Boolean = false,
    val EmailErrorText: String = Constants.BLANK_SPACE,
    val PasswordErrorText: String = Constants.BLANK_SPACE
)