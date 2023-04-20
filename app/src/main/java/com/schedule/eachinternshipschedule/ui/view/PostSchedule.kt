package com.schedule.eachinternshipschedule.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.schedule.eachinternshipschedule.ui.viewmodel.PostScheduleUiState
import com.schedule.eachinternshipschedule.ui.viewmodel.PostScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "PostScheduleScreen")
@Composable
fun PostScheduleScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: PostScheduleViewModel = hiltViewModel()
) {
    val uiState: PostScheduleUiState by viewModel.uiState.collectAsState()
    //選考に必要なデータ
    val routeItems =
        listOf("選考を選択してください", "書類", "一次面接", "二次面接", "三次面接", "最終面接")
    //選考状況に必要なデータ
    val statusItems = listOf("選考状況を選択してください", "実施日(提出)", "通過")

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
        }
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
                value = uiState.schedule.companyName,
                onValueChange = { value ->
                    viewModel.onCompanyNameValueChange(value)
                },
                isError = false,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PostScheduleEditField(
                label = "インターンシップ名",
                value = uiState.schedule.internshipName,
                onValueChange = { value ->
                    viewModel.onInternshipNameValueChange(value)
                },
                isError = false,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PostScheduleEditField(
                label = "日付",
                value = uiState.schedule.date,
                onValueChange = { value ->
                    viewModel.onDateValueChange(value)
                },
                isError = false,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = uiState.routeExpanded,
                onExpandedChange = {
                    viewModel.onRouteExpandedChange(uiState.routeExpanded)
                },
            ) {
                OutlinedTextField(
                    value = uiState.schedule.route,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = uiState.routeExpanded,
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    label = { Text(text = "選考") }
                )
                ExposedDropdownMenu(
                    expanded = uiState.routeExpanded,
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
                expanded = uiState.statusExpanded,
                onExpandedChange = {
                    viewModel.onStatusExpandedChange(uiState.statusExpanded)
                },
            ) {
                OutlinedTextField(
                    value = uiState.schedule.routeStatus,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = uiState.statusExpanded,
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    label = { Text(text = "選考状況") }
                )
                ExposedDropdownMenu(
                    expanded = uiState.statusExpanded,
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
                    viewModel.insertSchedule(uiState.schedule)
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
    isError: Boolean
) {
    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = isError,
        supportingText = {

        })
}
