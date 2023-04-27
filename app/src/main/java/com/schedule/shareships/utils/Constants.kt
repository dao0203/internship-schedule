package com.schedule.shareships.utils

object Constants {
    const val PAGE_SIZE = 20
    val ROUTE_ITEMS: List<String> = listOf(
        "選考を選択してください",
        "書類",
        "一次面接",
        "二次面接",
        "三次面接",
        "最終面接",
    )
    val STATUS_ITEMS: List<String> = listOf(
        "選考状況を選択してください",
        "実施日(提出)",
        "選考中",
        "通過",
    )
    const val DROPDOWN_MENU_OF_INITIAL_STATE_INDEX = 0
    const val BLANK_SPACE = ""
    const val INPUT_ERROR_MSG = "入力してください"
}