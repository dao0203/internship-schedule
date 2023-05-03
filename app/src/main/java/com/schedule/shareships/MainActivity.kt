package com.schedule.shareships

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.schedule.shareships.ui.theme.EachInternshipScheduleTheme
import com.schedule.shareships.ui.view.PostScheduleScreen
import com.schedule.shareships.ui.view.ScheduleListScreen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EachInternshipScheduleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Routes.ScheduleListScreen.route,
                    ) {
                        //TODO: 画面遷移のアニメーションを追加する
                        composable(
                            route = Routes.ScheduleListScreen.route,

                            exitTransition = {
                                when (initialState.destination.route) {
                                    Routes.PostScheduleScreen.route -> {
                                        slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Start
                                        )
                                    }

                                    else -> null
                                }
                            },
                        ) {
                            ScheduleListScreen(navController = navController)
                        }
                        composable(
                            route = Routes.PostScheduleScreen.route,
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Routes.ScheduleListScreen.route -> {
                                        slideIntoContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Start
                                        )
                                    }

                                    else -> null
                                }
                            },
                            popExitTransition = {
                                when (targetState.destination.route) {
                                    Routes.ScheduleListScreen.route -> {
                                        slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.End
                                        )
                                    }

                                    else -> null
                                }
                            },
                        ) {
                            PostScheduleScreen(navController = navController)
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
