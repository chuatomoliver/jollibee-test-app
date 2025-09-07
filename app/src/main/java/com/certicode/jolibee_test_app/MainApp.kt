
package com.certicode.jolibee_test_app

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.certicode.jolibee_test_app.screens.HomeScreen
import com.certicode.jolibee_test_app.screens.LoginScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
    }
}
