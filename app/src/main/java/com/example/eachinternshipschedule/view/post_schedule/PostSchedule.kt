package com.example.eachinternshipschedule.view.post_schedule

import androidx.compose.foundation.layout.Column
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

    val routeItems = listOf<String>("書類", "一次面接", "二次面接", "三次面接", "最終面接", "")
    var selectedRouteItem: String? = null
    Scaffold(
        modifier = modifier.padding(
            start = 8.dp,
            end = 8.dp,
        ),
    )
    {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
        ) {
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Company Name") },
            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Internship Name") },
            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("日付") },

            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("状況") },
            )
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

