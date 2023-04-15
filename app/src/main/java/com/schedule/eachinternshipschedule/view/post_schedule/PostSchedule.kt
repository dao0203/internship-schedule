package com.schedule.eachinternshipschedule.view.post_schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.schedule.eachinternshipschedule.viewmodel.PostScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "PostScheduleScreen")
@Composable
fun PostScheduleScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    postScheduleViewModel: PostScheduleViewModel = viewModel()
) {
    var routeExpanded by remember { mutableStateOf(false) }
    var statusExpanded by remember { mutableStateOf(false) }

    //選考に必要なデータ
    val routeItems =
        listOf<String>("選考を選択してください", "書類", "一次面接", "二次面接", "三次面接", "最終面接")
    var selectedRouteItem by remember { mutableStateOf(routeItems[0]) }

    //選考状況に必要なデータ
    val statusItems = listOf<String>("選考状況を選択してください", "実施日(提出)", "通過")
    var selectedStatusItem by remember { mutableStateOf(statusItems[0]) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "スケジュール投稿",
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
                isError = false,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PostScheduleEditField(
                label = "インターンシップ名",
                isError = false,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PostScheduleEditField(
                label = "日付",
                isError = false,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = routeExpanded,
                onExpandedChange = { routeExpanded = !routeExpanded },
            ) {
                OutlinedTextField(
                    value = selectedRouteItem,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = routeExpanded,
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    label = { Text(text = "選考") }
                )
                ExposedDropdownMenu(
                    expanded = routeExpanded,
                    onDismissRequest = {
                        routeExpanded = false
                    }) {
                    repeat(routeItems.size) {
                        DropdownMenuItem(
                            text = {
                                Text(text = routeItems[it])
                            },
                            onClick = {
                                selectedRouteItem = routeItems[it]
                                routeExpanded = false
                            },
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = statusExpanded,
                onExpandedChange = { statusExpanded = !statusExpanded },
            ) {
                OutlinedTextField(
                    value = selectedStatusItem,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = statusExpanded,
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    label = { Text(text = "選考状況") }
                )
                ExposedDropdownMenu(
                    expanded = statusExpanded,
                    onDismissRequest = {
                        statusExpanded = false
                    }
                ) {
                    repeat(statusItems.size) {
                        DropdownMenuItem(
                            text = {
                                Text(text = statusItems[it])
                            },
                            onClick = {
                                selectedStatusItem = statusItems[it]
                                statusExpanded = false
                            },
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            ElevatedButton(
                onClick = { /*TODO*/ },
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
//    onTextFieldValueChange: (String) -> Unit,
    isError: Boolean
) {
    OutlinedTextField(
        value = "",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        label = { Text(label) },
        singleLine = true,
        isError = isError,
        supportingText = {

        })
}

