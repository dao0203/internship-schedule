package com.schedule.shareships.ui.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.schedule.shareships.Routes
import com.schedule.shareships.ui.viewmodel.LoginUiState
import com.schedule.shareships.ui.viewmodel.LoginViewModel

@ExperimentalMaterial3Api
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
        modifier: Modifier = Modifier,
        navController: NavController = rememberAnimatedNavController(),
        viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState: LoginUiState by viewModel.loginUiState.collectAsState()
    Column(
            modifier = modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
                modifier = modifier
                        .fillMaxWidth(),
                value = uiState.loginData.email,
                onValueChange = {
                    viewModel.onEmailValueChange(it)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                ),
                label = { Text(text = "メールアドレス") },
                isError = uiState.isEmailError,
                supportingText = {
                    if (uiState.isEmailError) {
                        Text(text = uiState.EmailErrorText)
                    }
                },
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        OutlinedTextField(
                modifier = modifier
                        .fillMaxWidth(),
                label = { Text(text = "パスワード") },
                value = uiState.loginData.password,
                singleLine = true,
                isError = uiState.isPasswordError,
                supportingText = {
                    if (uiState.isPasswordError) {
                        Text(text = uiState.PasswordErrorText)
                    }
                },
                //パスワードアイコンの表示切り替え
                visualTransformation = if (uiState.isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },

                keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                ),
                trailingIcon = {
                    IconButton(
                            onClick = {
                                viewModel.onPasswordVisibilityChange()
                            },
                    ) {
                        Icon(
                                imageVector = if (uiState.isPasswordVisible) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                },
                                contentDescription = "パスワード表示切り替え",
                        )
                    }
                },
                onValueChange = {
                    viewModel.onPasswordValueChange(it)
                },

                )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        ElevatedButton(
                modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                onClick = {
                    //TODO: ログイン処理
                    if (viewModel.validateForm()) {
                        navController.navigate(Routes.ScheduleListScreen.route)
                    }
                },
                colors = ButtonDefaults.elevatedButtonColors(MaterialTheme.colorScheme.primary),
        ) {
            Text(
                    text = "ログイン",
                    color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        Text(
                text = "アカウントをお持ちでない方はこちら",
                style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = modifier.padding(vertical = 8.dp))
        ElevatedButton(
                modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                onClick = {
                    //新規登録画面に遷移
                    navController.navigate(Routes.RegisterScreen.route)
                },
                colors = ButtonDefaults.elevatedButtonColors(MaterialTheme.colorScheme.primary),
        ) {
            Text(
                    text = "新規登録",
                    color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}
