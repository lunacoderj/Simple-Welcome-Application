package com.mad.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mad.app.screens.AboutScreen
import com.mad.app.screens.ContactScreen
import com.mad.app.screens.CoursesScreen
import com.mad.app.screens.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Courses : Screen("courses")
    object Contact : Screen("contact")
}

fun NavController.navigateToTopLevel(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateToTopLevel.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.About.route) {
            AboutScreen(navController)
        }
        composable(Screen.Courses.route) {
            CoursesScreen(navController)
        }
        composable(Screen.Contact.route) {
            ContactScreen(navController)
        }
    }
}
