package com.example.eachinternshipschedule.view.post_schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "PostScheduleScreen")
@Composable
fun PostScheduleScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    var routeExpanded by remember {
        mutableStateOf(false)
    }
    var statusExpanded by remember { mutableStateOf(false) }

    val routeItems = listOf<String>("書類", "一次面接", "二次面接", "三次面接", "最終面接")
    var selectedRouteItem: String? = null
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "スケジュール投稿",
                    ) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
        ) {
            PostScheduleEditField(
                label = "会社名",
                isError = false,
            )
            PostScheduleEditField(
                label = "インターンシップ名",
                isError = false,
            )
            PostScheduleEditField(
                label = "日付",
                isError = false,
            )
            PostScheduleEditField(
                label = "選考状況",
                isError = false,
            )
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

