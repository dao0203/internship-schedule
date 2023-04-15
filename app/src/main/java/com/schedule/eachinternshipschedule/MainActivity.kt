package com.schedule.eachinternshipschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.schedule.eachinternshipschedule.ui.theme.EachInternshipScheduleTheme
import com.schedule.eachinternshipschedule.view.post_schedule.PostScheduleScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EachInternshipScheduleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHost = rememberNavController()
                    NavHost(
                        navController = navHost,
                        startDestination = Routes.PostScheduleScreen.route
                    ) {
                        composable(Routes.ScheduleListScreen.route) {}
                        composable(Routes.PostScheduleScreen.route){
                            PostScheduleScreen(navController = navHost)
                        }
                        }
                    }
                }
            }
        }
    }

    sealed class Routes(val route: String) {
        object PostScheduleScreen : Routes("postScheduleScreen")
        object ScheduleListScreen : Routes("scheduleListScreen")
    }