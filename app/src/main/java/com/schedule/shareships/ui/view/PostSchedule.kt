package com.schedule.shareships.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.schedule.shareships.Routes
import com.schedule.shareships.ui.viewmodel.PostScheduleUiState
import com.schedule.shareships.ui.viewmodel.PostScheduleViewModel
import com.schedule.shareships.ui.viewmodel.TextFieldError
import com.schedule.shareships.ui.viewmodel.TextFieldErrorMsg
import com.schedule.shareships.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "PostScheduleScreen")
@Composable
fun PostScheduleScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: PostScheduleViewModel = hiltViewModel()
) {
    //テキストフィールドのUI状態やローディング状態を管理する状態管理
    val textFieldUiState: PostScheduleUiState by viewModel.textFieldUiState.collectAsState()

    //テキストフィールドのエラー状態を管理する状態管理
    val textFieldErrorUiState: TextFieldError by viewModel.textFieldErrorUiState.collectAsState()

    //テキストフィールドのエラーメッセージ状態を管理する状態管理
    val textFieldErrorMsgUiState: TextFieldErrorMsg by viewModel.textFieldErrorMsgUiState.collectAsState()

    //投稿ボタンが押されて処理が終わった後のイベント
    LaunchedEffect(key1 = Unit){
        viewModel.onPressedPostButtonEvent.collect{
            //投稿ボタンの処理が正常に行われたら、リスト画面に戻る
            navController.popBackStack(
                Routes.ScheduleListScreen.route,
                inclusive = true
            )
        }
    }


    //選考に必要なデータ
    val routeItems = Constants.ROUTE_ITEMS
    //選考状況に必要なデータ
    val statusItems = Constants.STATUS_ITEMS

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "スケジュール投稿",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.popBackStack()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                content = {
                    Text(
                        text = "戻る",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            )
        },
    )
    {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PostScheduleEditField(
                label = "会社名",
                value = textFieldUiState.schedule.companyName,
                onValueChange = { value ->
                    viewModel.onCompanyNameValueChange(value)
                },
                isError = textFieldErrorUiState.isCompanyNameValid,
                errorMsg = textFieldErrorMsgUiState.companyName,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PostScheduleEditField(
                label = "インターンシップ名",
                value = textFieldUiState.schedule.internshipName,
                onValueChange = { value ->
                    viewModel.onInternshipNameValueChange(value)
                },
                isError = textFieldErrorUiState.isInternshipName,
                errorMsg = textFieldErrorMsgUiState.internshipName,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PostScheduleEditField(
                label = "日付",
                value = textFieldUiState.schedule.date,
                onValueChange = { value ->
                    viewModel.onDateValueChange(value)
                },
                isError = textFieldErrorUiState.isDateValid,
                errorMsg = textFieldErrorMsgUiState.date,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = textFieldUiState.routeExpanded,
                onExpandedChange = {
                    viewModel.onRouteExpandedChange(textFieldUiState.routeExpanded)
                },
            ) {
                OutlinedTextField(
                    value = textFieldUiState.schedule.route,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = textFieldUiState.routeExpanded,
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    label = { Text(text = "選考") },
                    isError = textFieldErrorUiState.isRouteValid,
                    supportingText = {
                        if (textFieldErrorUiState.isRouteValid) {
                            Text(text = textFieldErrorMsgUiState.route)
                        }
                    }
                )
                ExposedDropdownMenu(
                    expanded = textFieldUiState.routeExpanded,
                    onDismissRequest = {
                        viewModel.onRouteDismissRequest()
                    }) {
                    repeat(routeItems.size) {
                        DropdownMenuItem(
                            text = {
                                Text(text = routeItems[it])
                            },
                            onClick = {
                                viewModel.onRouteValueChange(routeItems[it])
                            },
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = textFieldUiState.statusExpanded,
                onExpandedChange = {
                    viewModel.onStatusExpandedChange(textFieldUiState.statusExpanded)
                },
            ) {
                OutlinedTextField(
                    value = textFieldUiState.schedule.routeStatus,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = textFieldUiState.statusExpanded,
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    label = { Text(text = "選考状況") },
                    isError = textFieldErrorUiState.isRouteStatusValid,
                    supportingText = {
                        if (textFieldErrorUiState.isRouteStatusValid) {
                            Text(text = textFieldErrorMsgUiState.routeStatus)
                        }
                    }
                )
                ExposedDropdownMenu(
                    expanded = textFieldUiState.statusExpanded,
                    onDismissRequest = {
                        viewModel.onStatusDismissRequest()
                    }
                ) {
                    repeat(statusItems.size) {
                        DropdownMenuItem(
                            text = {
                                Text(text = statusItems[it])
                            },
                            onClick = {
                                viewModel.onStatusValueChange(statusItems[it])
                            },
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            ElevatedButton(
                onClick = {
                    viewModel.onPressedPostButton(textFieldUiState.schedule)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "投稿する")
            }
        }

    }
}

@ExperimentalMaterial3Api
@Composable
fun PostScheduleEditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMsg: String
) {
    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMsg)
            }
        })
}

