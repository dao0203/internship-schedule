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
            isEmailError = false
        )
    }

    fun onPasswordValueChange(completedText: String) {
        _loginUiState.value = _loginUiState.value.copy(
            loginData = _loginUiState.value.loginData.copy(
                password = completedText
            ),
            isPasswordError = false
        )
    }

    fun validateForm(): Boolean {
        val email = loginUiState.value.loginData.email
        val password = loginUiState.value.loginData.password

        //Emailのバリデーション
        if (email.isEmpty()) {
            _loginUiState.value = _loginUiState.value.copy(
                isEmailError = true,
                EmailErrorText = "メールアドレスを入力してください"
            )
        } else if (email.contains(Regex("\"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}\""))
        ) {
            _loginUiState.value = _loginUiState.value.copy(
                isEmailError = true,
                EmailErrorText = "メールアドレスの形式が正しくありません"
            )
        }

        //Passwordのバリデーション
        if (password == Constants.BLANK_SPACE) {
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
    val loginData: LoginData = LoginData(
        email = Constants.BLANK_SPACE,
        password = Constants.BLANK_SPACE
    ),
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val EmailErrorText: String = Constants.BLANK_SPACE,
    val PasswordErrorText: String = Constants.BLANK_SPACE
)