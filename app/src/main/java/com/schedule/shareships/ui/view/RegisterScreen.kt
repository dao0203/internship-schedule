package com.schedule.shareships.ui.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Preview("RegisterScreen")
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberAnimatedNavController(),
) {
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
            value = "",
            label = { Text(text = "メールアドレス") },
            onValueChange = {}
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //ユーザー名を入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = "",
            label = { Text(text = "ユーザ名") },
            onValueChange = {}
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //Github IDを入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = "",
            label = { Text(text = "Github ID") },
            onValueChange = {}
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //パスワードを入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = "",
            label = { Text(text = "パスワード") },
            onValueChange = {}
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //パスワードを再入力するOutlinedTextField
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = "",
            label = { Text(text = "パスワードを再入力") },
            onValueChange = {}
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        //登録ボタン
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = "",
            label = { Text(text = "登録") },
            onValueChange = {}
        )
    }
}