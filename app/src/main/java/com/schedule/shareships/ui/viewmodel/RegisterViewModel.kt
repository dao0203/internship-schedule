package com.schedule.shareships.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState = _registerUiState.asStateFlow()

    //Emailのテキストを管理するメソッド
    fun onEmailValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            email = completedText,
            isEmailError = false
        )
    }

    //UserNameのテキストを管理するメソッド
    fun onUserNameValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            userName = completedText,
            isUserNameError = false
        )
    }

    //GithubIdのテキストを管理するメソッド
    fun onGithubIdValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            githubId = completedText,
            isGithubIdError = false
        )
    }

    //Passwordのテキストを管理するメソッド
    fun onPasswordValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            password = completedText,
            isPasswordError = false
        )
    }

    //再入力Passwordのテキストを管理するメソッド
    fun onReenteredPasswordValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            reenteredPassword = completedText,
            isReenteredPasswordError = false
        )
    }

    //バリデーションを行うメソッド
    fun validateForm(): Boolean {
        val email = registerUiState.value.email
        val userName = registerUiState.value.userName
        val githubId = registerUiState.value.githubId
        val password = registerUiState.value.password
        val reenteredPassword = registerUiState.value.reenteredPassword
        //Emailのバリデーション
        if (email.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isEmailError = true,
            )
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _registerUiState.value = _registerUiState.value.copy(
                isEmailError = true,
            )
        }
        //UserNameのバリデーション
        if (userName.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isUserNameError = true,
            )
        }
        //GithubIdのバリデーション
        if (githubId.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isGithubIdError = true,
            )
        }
        //Passwordのバリデーション
        if (password.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isPasswordError = true,
            )
        }
        //再入力Passwordのバリデーション
        if (reenteredPassword.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isReenteredPasswordError = true,
            )
        } else if (password != reenteredPassword) {
            _registerUiState.value = _registerUiState.value.copy(
                isReenteredPasswordError = true,
            )
        }

        //バリデーション結果を返す
        return registerUiState.value.run {
            !isEmailError &&
                    !isUserNameError &&
                    !isGithubIdError &&
                    !isPasswordError &&
                    !isReenteredPasswordError
        }
    }

}

data class RegisterUiState(
    //記入項目
    val email: String = "",
    val userName: String = "",
    val githubId: String = "",
    val password: String = "",
    val reenteredPassword: String = "",

    //エラー項目
    val isEmailError: Boolean = false,
    val isUserNameError: Boolean = false,
    val isGithubIdError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isReenteredPasswordError: Boolean = false,
)
