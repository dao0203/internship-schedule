package com.schedule.shareships.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.schedule.shareships.model.RegisterData
import com.schedule.shareships.utils.Constants
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
            registerData = _registerUiState.value.registerData.copy(
                email = completedText
            )
        )
    }

    //UserNameのテキストを管理するメソッド
    fun onUserNameValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            registerData = _registerUiState.value.registerData.copy(
                userName = completedText
            )
        )
    }

    //GithubIdのテキストを管理するメソッド
    fun onGithubIdValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            registerData = _registerUiState.value.registerData.copy(
                githubId = completedText
            )
        )
    }

    //Passwordのテキストを管理するメソッド
    fun onPasswordValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            registerData = _registerUiState.value.registerData.copy(
                password = completedText
            )
        )
    }

    //再入力Passwordのテキストを管理するメソッド
    fun onReenteredPasswordValueChange(completedText: String) {
        _registerUiState.value = _registerUiState.value.copy(
            reenteredPassword = completedText
        )
    }

    //バリデーションを行うメソッド
    fun validateForm(): Boolean {
        val email = registerUiState.value.registerData.email
        val userName = registerUiState.value.registerData.userName
        val githubId = registerUiState.value.registerData.githubId
        val password = registerUiState.value.registerData.password
        val reenteredPassword = registerUiState.value.reenteredPassword
        //Emailのバリデーション
        if (email.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isEmailError = true,
                EmailErrorText = Constants.INPUT_ERROR_MSG
            )
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _registerUiState.value = _registerUiState.value.copy(
                isEmailError = true,
                EmailErrorText = Constants.EMAIL_INVALID_ERROR_MSG
            )
        }
        //UserNameのバリデーション
        if (userName.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isUserNameError = true,
                UserNameErrorText = Constants.INPUT_ERROR_MSG
            )
        }
        //GithubIdのバリデーション
        if (githubId.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isGithubIdError = true,
                GithubIdErrorText = Constants.INPUT_ERROR_MSG
            )
        }
        //Passwordのバリデーション
        if (password.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isPasswordError = true,
                PasswordErrorText = Constants.INPUT_ERROR_MSG
            )
        }
        //再入力Passwordのバリデーション
        if (reenteredPassword.isEmpty()) {
            _registerUiState.value = _registerUiState.value.copy(
                isReenteredPasswordError = true,
                ReenteredPasswordErrorText = Constants.INPUT_ERROR_MSG
            )
        } else if (password != reenteredPassword) {
            _registerUiState.value = _registerUiState.value.copy(
                isReenteredPasswordError = true,
                ReenteredPasswordErrorText = Constants.PASSWORD_NOT_MATCH_ERROR_MSG
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
    val registerData: RegisterData = RegisterData(
        email = Constants.BLANK_SPACE,
        userName = Constants.BLANK_SPACE,
        githubId = Constants.BLANK_SPACE,
        password = Constants.BLANK_SPACE
    ),
    val reenteredPassword: String = Constants.BLANK_SPACE,
    val isEmailError: Boolean = false,
    val isUserNameError: Boolean = false,
    val isGithubIdError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isReenteredPasswordError: Boolean = false,
    val EmailErrorText: String = Constants.BLANK_SPACE,
    val UserNameErrorText: String = Constants.BLANK_SPACE,
    val GithubIdErrorText: String = Constants.BLANK_SPACE,
    val PasswordErrorText: String = Constants.BLANK_SPACE,
    val ReenteredPasswordErrorText: String = Constants.BLANK_SPACE
)
