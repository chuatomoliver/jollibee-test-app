package com.certicode.jolibee_test_app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.HorizontalDivider
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.certicode.jolibee_test_app.presentation.category.CategoryScreen
import com.certicode.jolibee_test_app.presentation.contacts_business.ContactListBusinessScreen
import com.certicode.jolibee_test_app.presentation.contacts_people.ContactListPeopleScreen
import com.certicode.jolibee_test_app.presentation.tags.TagScreen
import com.certicode.jolibee_test_app.presentation.tasks.TaskListScreen

// Sealed class to represent different screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // State to track the currently selected tab
    val screens = listOf(HomeScreenItems.TaskList, HomeScreenItems.Completed, HomeScreenItems.ContactsPeople,HomeScreenItems.ContactsBusiness, HomeScreenItems.Tags, HomeScreenItems.Categories)
    var selectedScreen by remember { mutableStateOf<HomeScreenItems>(HomeScreenItems.TaskList) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true, // You can control if the drawer can be opened with a swipe gesture
        drawerContent = {
            ModalDrawerSheet {
                // Drawer header
                Text("Jollibee App", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                // Drawer items
                NavigationDrawerItem(
                    label = { Text("Sign Out") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("login_screen") {
                                // This is crucial for a smooth user experience.
                                // It clears the back stack up to the 'home' destination,
                                // so the user can't navigate back to the app after logging out.
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Jollibee",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open navigation drawer"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black
                    )
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color(0xFFF0F0F0))
                ) {
                    // Tab Row
                    ScrollableTabRow(
                        selectedTabIndex = screens.indexOf(selectedScreen),
                        containerColor = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        screens.forEachIndexed { index, screen ->
                            Tab(
                                selected = selectedScreen == screen,
                                onClick = { selectedScreen = screen }
                            ) {
                                Text(
                                    text = screen.title,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }

                    // Content based on the selected screen
                    when (selectedScreen) {
                        is HomeScreenItems.TaskList -> TaskListScreen(navController, listType = "open")
                        is HomeScreenItems.Completed -> TaskListScreen(navController, listType = "complete")
                        is HomeScreenItems.ContactsPeople -> ContactListPeopleScreen(navController)
                        is HomeScreenItems.ContactsBusiness -> ContactListBusinessScreen(navController)
                        is HomeScreenItems.Tags -> TagScreen(navController)
                        is HomeScreenItems.Categories -> CategoryScreen(navController)
                    }
                }
            }
        )
    }
}



@Composable
fun TaskButton(
    modifier: Modifier = Modifier,
    isTaskCompleted: Boolean,
    onCompleteClick: () -> Unit,
    onReopenClick: () -> Unit
) {
    Button(
        onClick = {
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isTaskCompleted) Color.Black else Color.Gray
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
    ) {
        Text(
            text = if (isTaskCompleted) "Update" else "Complete",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }

    Button(
        onClick = {
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isTaskCompleted) Color.Black else Color.Gray
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
    ) {
        Text(
            text = if (isTaskCompleted) "Delete" else "Complete",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}



// The main preview for the whole HomeScreen
@Preview(showBackground = true)
@Composable
fun MyBookingsScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}