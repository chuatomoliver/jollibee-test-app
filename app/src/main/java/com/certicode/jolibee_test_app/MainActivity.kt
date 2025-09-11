package com.certicode.jolibee_test_app

import TagAddScreen
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
import com.certicode.jolibee_test_app.presentation.ContactPeopleAddScreen
import com.certicode.jolibee_test_app.presentation.HomeScreen
import com.certicode.jolibee_test_app.presentation.LoginScreen
import com.certicode.jolibee_test_app.presentation.category.CategoryAddScreen
import com.certicode.jolibee_test_app.presentation.category.CategoryScreen
import com.certicode.jolibee_test_app.presentation.contacts_business.ContactBusinessAddScreen
import com.certicode.jolibee_test_app.presentation.contacts_business.ContactListBusinessScreen
import com.certicode.jolibee_test_app.presentation.contacts_business.ContactListUpdateBusinessScreen
import com.certicode.jolibee_test_app.presentation.contacts_people.ContactListPeopleScreen
import com.certicode.jolibee_test_app.presentation.contacts_people.ContactListUpdatePeopleScreen
import com.certicode.jolibee_test_app.presentation.tags.TagScreen
import com.certicode.jolibee_test_app.presentation.tasks.TaskAddScreen
import com.certicode.jolibee_test_app.presentation.tasks.TaskListScreen
import com.certicode.jolibee_test_app.ui.theme.QuoteAppTheme
import com.certicode.jolibee_test_app.presentation.ui_screen.QuoteScreen
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
                        // people update screen
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


                        composable(
                            route = "contact_edit_business_screen/{businessId}",
                            arguments = listOf(navArgument("businessId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val businessId = backStackEntry.arguments?.getLong("businessId") ?: 0L
                            ContactListUpdateBusinessScreen(
                                navController = navController,
                                businessId = businessId
                            )
                        }

                        composable(Screen.TagList.route) {
                            TagScreen(navController)
                        }

                        composable(Screen.TagAdd.route) {
                            TagAddScreen(navController)
                        }

                        composable(Screen.CategoryList.route) {
                            CategoryScreen(navController)
                        }
                        composable(Screen.CategoryAdd.route) {
                            CategoryAddScreen(navController)
                        }

                    }
                }
            }
        }
    }
}
