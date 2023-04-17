package com.schedule.eachinternshipschedule.view.schedule_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
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
                    ScheduleListScreenWhenSuccess(
                        scheduleList = (uiState as ScheduleListUiState.Success).scheduleList,
                    )
                }
                is ScheduleListUiState.Error -> {}
            }
        }

    }

}