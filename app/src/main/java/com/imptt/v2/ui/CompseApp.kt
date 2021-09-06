package com.imptt.v2.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.imptt.v2.ui.pages.*
import com.imptt.v2.ui.theme.ImPttComposeTheme

/**
 *  author : ciih
 *  date : 2021/9/6 10:44 上午
 *  description :
 */

@Composable
fun ComposeApp(){
    val navController = rememberNavController()
    ImPttComposeTheme {
        NavHost(
            navController = navController,
            startDestination = DASHBOARD
        ) {
            composable(LOGIN) {
                LoginPage()
            }
            composable(DASHBOARD) {
                DashboardPage()
            }
            composable(
                USER_INFO,
                arguments = listOf(
                    navArgument(USER_ID_PARAM) { type = NavType.IntType }
                )
            ) {
                UserInfoPage()
            }
        }
    }
}