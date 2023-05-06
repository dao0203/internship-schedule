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
    val isEmailError: Boolean = false,
    val isUserNameError: Boolean = false,
    val isGithubIdError: Boolean = false,
    val isPasswordError: Boolean = false,
    val EmailErrorText: String = "",
    val UserNameErrorText: String = "",
    val GithubIdErrorText: String = "",
    val PasswordErrorText: String = "",
)
