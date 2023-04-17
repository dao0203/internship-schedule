package com.schedule.eachinternshipschedule.view.schedule_list

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.repository.DefaultFirestoreRepository
import com.schedule.eachinternshipschedule.viewmodel.ScheduleListUiState
import com.schedule.eachinternshipschedule.viewmodel.ScheduleListViewModel
import com.schedule.eachinternshipschedule.viewmodel.ScheduleListViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleListScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    scheduleListViewModel: ScheduleListViewModel = viewModel(
        factory = ScheduleListViewModelFactory(
            repository = DefaultFirestoreRepository(
                firestore = FirebaseFirestore.getInstance(),
            ),
        ),
    )
) {
    val uiState: ScheduleListUiState by scheduleListViewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.padding(
            vertical = 8.dp
        )
    ) {
        Box(
            modifier = modifier.padding(it),
        ) {
            when(uiState){
                is ScheduleListUiState.Loading -> {
                    ScheduleListScreenWhenLoading()
                }
                is ScheduleListUiState.Success -> {
                    LazyColumn(
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        items((uiState as ScheduleListUiState.Success).scheduleList.size) { schedule ->
                            ScheduleItem(schedule = (uiState as ScheduleListUiState.Success).scheduleList[schedule])
                        }
                    }
                }
                is ScheduleListUiState.Error -> {

                }
            }
        }

    }
}

@Composable
fun ScheduleListScreenWhenLoading(
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
