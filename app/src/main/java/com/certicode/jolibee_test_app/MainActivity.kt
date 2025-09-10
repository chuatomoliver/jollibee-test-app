package com.certicode.jolibee_test_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.certicode.jolibee_test_app.screens.ContactPeopleAddScreen
import com.certicode.jolibee_test_app.screens.HomeScreen
import com.certicode.jolibee_test_app.screens.LoginScreen
import com.certicode.jolibee_test_app.screens.contacts_business.ContactBusinessAddScreen
import com.certicode.jolibee_test_app.screens.contacts_business.ContactListBusinessScreen
import com.certicode.jolibee_test_app.screens.contacts_people.ContactListPeopleScreen
import com.certicode.jolibee_test_app.screens.contacts_people.ContactListUpdatePeopleScreen
import com.certicode.jolibee_test_app.screens.tasks.TaskAddScreen
import com.certicode.jolibee_test_app.screens.tasks.TaskListScreen
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
                        composable(Screen.ContactListPeople.route) {
                            ContactListPeopleScreen(navController)
                        }
                        composable(Screen.ContactListBusiness.route) {
                            ContactListBusinessScreen(navController)
                        }
                        composable(Screen.ContactAddPeople.route) {
                            ContactPeopleAddScreen(navController)
                        }
                        composable(Screen.ContactAddBusiness.route) {
                            ContactBusinessAddScreen(navController)
                        }
                        composable(
                            route = "contact_update_people_screen/{personId}",
                            arguments = listOf(navArgument("personId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val personId = backStackEntry.arguments?.getString("personId") ?: ""
                            ContactListUpdatePeopleScreen(
                                navController = navController,
                                personId = personId
                            )
                        }
                    }
                }
            }
        }
    }
}
