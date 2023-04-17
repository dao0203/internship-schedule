package com.schedule.eachinternshipschedule.view.schedule_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.schedule.eachinternshipschedule.model.Schedule

@ExperimentalMaterial3Api
@Composable
fun ScheduleListScreenWhenSuccess(
    modifier: Modifier = Modifier,
    scheduleList: List<Schedule>
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(scheduleList.size) { schedule ->
            ScheduleItem(schedule = scheduleList[schedule])
        }
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