package com.schedule.shareships

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.schedule.shareships.ui.view.PostScheduleScreen
import com.schedule.shareships.ui.view.ScheduleListScreen

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
                color = MaterialTheme.colorScheme.onPrimary
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
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary)
    )
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EachInternshipScheduleApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = Routes.values().find { it.route == backStackEntry.value?.destination?.route }
        ?: Routes.ScheduleListScreen
    Scaffold(
        topBar = {
            EachInternshipScheduleAppBar(
                currentRoute = currentRoute,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {navController.navigateUp()},
            )
        },
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = Routes.ScheduleListScreen.route,
            modifier = modifier.padding(it),
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