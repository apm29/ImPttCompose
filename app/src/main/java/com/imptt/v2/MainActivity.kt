package com.imptt.v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imptt.v2.ui.pages.LOGIN
import com.imptt.v2.ui.pages.LoginPage
import com.imptt.v2.ui.theme.ImPttComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ImPttComposeTheme {
               NavHost(
                   navController = navController,
                   startDestination = LOGIN
               ){
                    composable(LOGIN){
                        LoginPage(navController)
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