package com.imptt.v2.ui.pages

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 *  author : ciih
 *  date : 2021/9/3 4:18 下午
 *  description :
 */

const val LOGIN = "login"

@Composable
fun LoginPage(navController: NavHostController) {
    Text(text = "登录")
}