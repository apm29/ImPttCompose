package com.imptt.v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.imptt.v2.ui.pages.*
import com.imptt.v2.ui.theme.ImPttComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ImPttComposeTheme {
                NavHost(
                    navController = navController,
                    startDestination = DASHBOARD
                ) {
                    composable(LOGIN) {
                        LoginPage(navController)
                    }
                    composable(DASHBOARD) {
                        DashboardPage(navController)
                    }
                    composable(
                        USER_INFO,
                        arguments = listOf(
                            navArgument(USER_ID_PARAM) { type = NavType.IntType }
                        )
                    ) {
                        UserInfoPage(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImPttComposeTheme {
        Greeting("Android")
    }
}