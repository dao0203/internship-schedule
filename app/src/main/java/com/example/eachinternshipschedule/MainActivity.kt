package com.example.eachinternshipschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eachinternshipschedule.ui.theme.EachInternshipScheduleTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
                        startDestination = Routes.ScheduleListScreen.route
                    ) {
                        composable(Routes.ScheduleListScreen.route) {}
                        composable(Routes.PostScheduleScreen.route){}
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
