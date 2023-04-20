package com.schedule.eachinternshipschedule.ui.view.schedule_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.viewmodel.ScheduleListViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.schedule.eachinternshipschedule.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleListScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    scheduleListViewModel: ScheduleListViewModel = viewModel()
) {
    val items: LazyPagingItems<Schedule> = scheduleListViewModel.items.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "スケジュール一覧",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.PostScheduleScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    text = "投稿",
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
    ) {
        Box(
            modifier = modifier.padding(it),
        ) {
            when (items.loadState.refresh) {
                is LoadState.Loading -> {
                    ScheduleListScreenWhenLoading()
                }

                is LoadState.NotLoading -> {
                    LazyColumn(
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                    {
                        items(items.itemCount) { schedule ->
                            ScheduleItem(schedule = items[schedule]!!)
                        }
                    }
                }

                is LoadState.Error -> {
                    Text(
                        text = "Error",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun ScheduleListScreenWhenLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@ExperimentalMaterial3Api
@Composable
fun ScheduleItem(
    schedule: Schedule,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        onClick = {

        }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = schedule.companyName,
            )
            Text(
                text = schedule.internshipName,
            )
            Text(
                text = schedule.date,
            )
            Text(
                text = schedule.route,
            )
            Text(
                text = schedule.routeStatus,
            )
        }
    }
}
