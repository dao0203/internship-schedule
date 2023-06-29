package com.schedule.shareships.ui.view

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.schedule.shareships.Routes
import com.schedule.shareships.ui.viewmodel.PostScheduleUiState
import com.schedule.shareships.ui.viewmodel.PostScheduleViewModel
import com.schedule.shareships.ui.viewmodel.TextFieldError
import com.schedule.shareships.utils.Constants
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Preview(name = "PostScheduleScreen")
@Composable
fun PostScheduleScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberAnimatedNavController(),
    viewModel: PostScheduleViewModel = hiltViewModel()
) {
    //テキストフィールドのUI状態やローディング状態を管理する状態管理
    val textFieldUiState: PostScheduleUiState by viewModel.textFieldUiState.collectAsState()

    //テキストフィールドのエラー状態を管理する状態管理
    val textFieldErrorUiState: TextFieldError by viewModel.textFieldErrorUiState.collectAsState()

    //選考に必要なデータ
    val routeItems = Constants.ROUTE_ITEMS
    //選考状況に必要なデータ
    val statusItems = Constants.STATUS_ITEMS

    //ダイアログの状態管理
    val dateDialogState = rememberMaterialDialogState()

    val context = LocalContext.current

    //投稿ボタンが押されて処理が終わった後のイベント
    LaunchedEffect(key1 = Unit) {
        viewModel.onPressedPostButtonEvent.collect {
            if (it) {
                //トーストテキストを表示
                Toast.makeText(
                    context,
                    "投稿が完了しました",
                    Toast.LENGTH_SHORT
                ).show()
                //投稿ボタンの処理が正常に行われたら、リスト画面に遷移し、これまでの画面を全て削除する
                navController.navigate(Routes.ScheduleListScreen.route) {
                    popUpTo(Routes.ScheduleListScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //日付を選択するダイアログ
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton("OK")
                negativeButton("キャンセル")
            },

            ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "日付を選択してください",
                onDateChange = { date ->
                    viewModel.onDateValueChange(
                        year = date.year,
                        month = date.monthValue,
                        dayOfMonth = date.dayOfMonth
                    )
                }
            )
        }
        Spacer(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        PostScheduleEditField(
            label = "会社名",
            value = textFieldUiState.schedule.companyName,
            onValueChange = { value ->
                viewModel.onCompanyNameValueChange(value)
            },
            isError = textFieldErrorUiState.isCompanyNameError,
        )
        Spacer(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        PostScheduleEditField(
            label = "インターンシップ名",
            value = textFieldUiState.schedule.internshipName,
            onValueChange = { value ->
                viewModel.onInternshipNameValueChange(value)
            },
            isError = textFieldErrorUiState.isInternshipNameError,
        )
        Spacer(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = textFieldUiState.schedule.date,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            label = { Text("日付") },
            singleLine = true,
            isError = textFieldErrorUiState.isDateError,
            supportingText = {
                if (textFieldErrorUiState.isDateError) {
                    Text(text = Constants.INPUT_ERROR_MSG)
                }
            },
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        dateDialogState.show()
                    },
                    content = {
                        Icon(
                            //カレンダーのアイコン
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                        )
                    },
                )
            }
        )
        Spacer(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = textFieldUiState.routeExpanded,
            onExpandedChange = { viewModel.onRouteExpandedChange(textFieldUiState.routeExpanded) }
        ) {
            OutlinedTextField(
                value = textFieldUiState.schedule.route,
                readOnly = true,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = textFieldUiState.routeExpanded,
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                label = { Text(text = "選考") },
                isError = textFieldErrorUiState.isRouteError,
                supportingText = {
                    if (textFieldErrorUiState.isRouteError) {
                        Text(text = Constants.INPUT_ERROR_MSG)
                    }
                }
            )
            ExposedDropdownMenu(
                expanded = textFieldUiState.routeExpanded,
                onDismissRequest = {
                    viewModel.onRouteDismissRequest()
                }) {
                repeat(routeItems.size) {
                    DropdownMenuItem(
                        text = {
                            Text(text = routeItems[it])
                        },
                        onClick = {
                            viewModel.onRouteValueChange(routeItems[it])
                        },
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = textFieldUiState.statusExpanded,
            onExpandedChange = { viewModel.onStatusExpandedChange(textFieldUiState.statusExpanded) }
        ) {
            OutlinedTextField(
                value = textFieldUiState.schedule.routeStatus,
                readOnly = true,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = textFieldUiState.statusExpanded,
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                label = { Text(text = "選考状況") },
                isError = textFieldErrorUiState.isRouteStatusError,
                supportingText = {
                    if (textFieldErrorUiState.isRouteStatusError) {
                        Text(text = Constants.INPUT_ERROR_MSG)
                    }
                }
            )
            ExposedDropdownMenu(
                expanded = textFieldUiState.statusExpanded,
                onDismissRequest = {
                    viewModel.onStatusDismissRequest()
                }
            ) {
                repeat(statusItems.size) {
                    DropdownMenuItem(
                        text = {
                            Text(text = statusItems[it])
                        },
                        onClick = {
                            viewModel.onStatusValueChange(statusItems[it])
                        },
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        ElevatedButton(
            onClick = {
                if (viewModel.validateForm()) {
                    viewModel.onPressedPostButton()
                }
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        ) {
            Text(text = "投稿する")
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun PostScheduleEditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMsg: String = Constants.INPUT_ERROR_MSG
) {
    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMsg)
            }
        },
    )
}
