package com.imptt.v2.ui.pages

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.imptt.v2.R

/**
 *  author : ciih
 *  date : 2021/9/3 4:18 下午
 *  description :
 */

const val DASHBOARD = "dashboard"
const val CHANNELS = "channels"
const val CONTACTS = "contacts"
const val SETTINGS = "settings"

val items = listOf(
    Dashboard.Channels,
    Dashboard.Contacts,
    Dashboard.Settings,
)

@Composable
fun DashboardPage(navController: NavHostController) {
    val hostController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation() {
                val navBackStackEntry by hostController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            hostController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(hostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
    ) {
        NavHost(
            hostController, startDestination = Dashboard.Channels.route,
            Modifier.padding(it)
        ) {
            composable(Dashboard.Channels.route) {  }
            composable(Dashboard.Contacts.route) {  }
            composable(Dashboard.Settings.route) {  }
        }

    }
}

sealed class Dashboard(val route: String, @StringRes val resourceId: Int) {
    object Channels : Dashboard(CHANNELS, R.string.channel_page_name)
    object Contacts : Dashboard(CONTACTS, R.string.contact_page_name)
    object Settings : Dashboard(SETTINGS, R.string.setting_page_name)
}