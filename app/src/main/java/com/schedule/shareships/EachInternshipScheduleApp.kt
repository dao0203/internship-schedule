package com.schedule.shareships

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class Routes(val route: String, val title: String) {
    PostScheduleScreen("postScheduleScreen", "スケジュール投稿"),
    ScheduleListScreen("scheduleListScreen", "スケジュール一覧"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachInternshipScheduleAppBar(
    currentRoute: Routes,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = currentRoute.title,
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "戻る",
                    )
                }
            }
        },
    )
}

@Composable
fun EachInternshipScheduleApp() {
}