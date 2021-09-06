package com.imptt.v2.ui.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 *  author : ciih
 *  date : 2021/9/3 4:28 下午
 *  description :
 */

const val USER_ID_PARAM = "userId"
const val USER_INFO_PATH_PREFIX = "userInfo"
const val USER_INFO = "$USER_INFO_PATH_PREFIX/{$USER_ID_PARAM}"

fun Int.asNavigationUrl(): String {
    return "$USER_INFO_PATH_PREFIX/$this"
}

@Composable
fun UserInfoPage() {
    LazyColumn(){

    }
}