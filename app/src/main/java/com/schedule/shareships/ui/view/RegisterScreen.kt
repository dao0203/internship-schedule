package com.schedule.shareships.ui.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.schedule.shareships.R
import com.schedule.shareships.Routes
import com.schedule.shareships.ui.viewmodel.RegisterUiState
import com.schedule.shareships.ui.viewmodel.RegisterViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Preview("RegisterScreen")
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberAnimatedNavController(),
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState: RegisterUiState by viewModel.registerUiState.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //メールアドレスを入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = uiState.email,
            label = { Text(text = "メールアドレス") },
            isError = uiState.isEmailError,
            supportingText = {
                if (uiState.isEmailError) {
                    Text(text = context.getString(R.string.empty_message))
                }
            },
            onValueChange = {
                viewModel.onEmailValueChange(it)
            }
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //ユーザー名を入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = uiState.userName,
            label = { Text(text = "ユーザ名") },
            isError = uiState.isUserNameError,
            supportingText = {
                if (uiState.isUserNameError) {
                    Text(text = context.getString(R.string.empty_message))
                }
            },
            onValueChange = {
                viewModel.onUserNameValueChange(it)
            }
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //Github IDを入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = uiState.githubId,
            label = { Text(text = "Github ID") },
            isError = uiState.isGithubIdError,
            supportingText = {
                if (uiState.isGithubIdError) {
                    Text(text = context.getString(R.string.empty_message))
                }
            },
            onValueChange = {
                viewModel.onGithubIdValueChange(it)
            }
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //パスワードを入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = uiState.password,
            label = { Text(text = "パスワード") },
            isError = uiState.isPasswordError,
            supportingText = {
                if (uiState.isPasswordError) {
                    Text(text = context.getString(R.string.empty_message))
                }
            },
            onValueChange = {
                viewModel.onPasswordValueChange(it)
            }
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //パスワードを再入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = uiState.reenteredPassword,
            label = { Text(text = "パスワードを再入力") },
            isError = uiState.isReenteredPasswordError,
            supportingText = {
                if (uiState.isReenteredPasswordError) {
                    Text(text = context.getString(R.string.empty_message))
                }
            },
            onValueChange = {
                viewModel.onReenteredPasswordValueChange(it)
            }
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //登録ボタン
        ElevatedButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                if (viewModel.validateForm()) {
                    //TODO:登録処理
                    navController.navigate(
                        route = Routes.ScheduleListScreen.route
                    ) {
                        popUpTo(Routes.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            },
            colors = ButtonDefaults.elevatedButtonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "登録",
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}