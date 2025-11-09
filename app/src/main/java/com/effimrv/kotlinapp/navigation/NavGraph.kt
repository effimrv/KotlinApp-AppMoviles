package com.effimrv.kotlinapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.effimrv.kotlinapp.ui.screens.DetailScreen
import com.effimrv.kotlinapp.ui.screens.FormScreen
import com.effimrv.kotlinapp.ui.screens.HomeScreen
import com.effimrv.kotlinapp.ui.screens.LoginScreen

object Destinations {
    const val LOGIN = "login"
    const val HOME = "home"
    const val FORM = "form"
    const val DETAIL = "detail/{itemId}"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.LOGIN) {
        composable(Destinations.LOGIN) { LoginScreen(navController) }
        composable(Destinations.HOME) { HomeScreen(navController) }
        composable(Destinations.FORM) { FormScreen(navController) }
        composable("detail/{itemId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("itemId")
            DetailScreen(navController, itemId = id)
        }
    }
}

