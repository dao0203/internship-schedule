package com.schedule.shareships.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
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
            email = completedText,
            isEmailError = false
        )
    }

    fun onPasswordValueChange(completedText: String) {
        _loginUiState.value = _loginUiState.value.copy(
            password = completedText,
            isPasswordError = false
        )
    }

    fun onPasswordVisibilityChange() {
        _loginUiState.value = _loginUiState.value.copy(
            isPasswordVisible = !_loginUiState.value.isPasswordVisible
        )
    }

    fun validateForm(): Boolean {
        val email = loginUiState.value.email
        val password = loginUiState.value.password

        //Emailのバリデーション
        if (email.isEmpty()) {
            _loginUiState.value = _loginUiState.value.copy(
                isEmailError = true,
                EmailErrorText = "メールアドレスを入力してください"
            )
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginUiState.value = _loginUiState.value.copy(
                isEmailError = true,
                EmailErrorText = "メールアドレスの形式が正しくありません"
            )
        }

        //Passwordのバリデーション
        if (password.isEmpty()) {
            _loginUiState.value = _loginUiState.value.copy(
                isPasswordError = true,
                PasswordErrorText = "パスワードを入力してください"
            )

        }
        //バリデーションが通ったら、正常に動作するようにする
        return loginUiState.value.run {
            !isEmailError && !isPasswordError
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val EmailErrorText: String = "",
    val PasswordErrorText: String = "",
    val isPasswordVisible: Boolean = false,
)
