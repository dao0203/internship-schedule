package com.schedule.shareships.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.schedule.shareships.model.RegisterData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
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
