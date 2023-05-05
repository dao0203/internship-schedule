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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.schedule.shareships.Routes

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberAnimatedNavController(),
) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Email",
            value = "",
            errorMsg = "Email is required",
            isError = false,
            onValueChange = {},
            isPassword = false,
            isVisible = false,
            onIconClick = {}
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        LoginTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Password",
            value = "",
            errorMsg = "Password is required",
            isError = false,
            onValueChange = {},
            isPassword = true,
            isVisible = false,
            onIconClick = {},
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        ElevatedButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        ) {
            Text(text = "ログイン")
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
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        ) {
            Text(text = "新規登録")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    errorMsg: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    isPassword: Boolean,
    isVisible: Boolean = false,
    onIconClick: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = isError,
        visualTransformation = if (isPassword) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = when (isPassword) {
                false -> KeyboardType.Email
                true -> KeyboardType.Password
            },
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { onIconClick() }) {
                    Icon(
                        imageVector = when (isVisible) {
                            false -> Icons.Filled.VisibilityOff
                            true -> Icons.Filled.Visibility
                        },
                        contentDescription = "パスワードアイコン",
                    )
                }
            }
        },
        supportingText = {
            if (isError) {
                Text(text = errorMsg)
            }
        })
}