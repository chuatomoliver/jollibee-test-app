package com.certicode.jolibee_test_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.screens.HomeScreen
import com.certicode.jolibee_test_app.screens.LoginScreen
import com.certicode.jolibee_test_app.screens.ui_task_add.TaskAddScreen
import com.certicode.jolibee_test_app.screens.ui_task_list.TaskListScreen
import com.certicode.jolibee_test_app.ui.theme.QuoteAppTheme
import com.certicode.jolibee_test_app.ui_screen.QuoteScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.Login.route) {
                        composable(Screen.Login.route) {
                            LoginScreen(navController)
                        }
                        composable(Screen.Home.route) {
                            HomeScreen(navController)
                        }
                        composable(Screen.TaskList.route) {
                            TaskListScreen(navController)
                        }
                        composable(Screen.TaskAdd.route) {
                            TaskAddScreen(navController)
                        }
                        composable(Screen.quoteScreen.route) {
                            QuoteScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            QuoteAppTheme {
//                QuoteScreen()
//            }
//        }
//    }
//}