package com.certicode.jolibee_test_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.HorizontalDivider
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.certicode.jolibee_test_app.screens.ui_task.TaskListScreen

// Sealed class to represent different screens
sealed class BookingScreen(val title: String) {
    object TaskList : BookingScreen("Task List")
    object Completed : BookingScreen("Completed")
    object Contacts : BookingScreen("Contacts")
    object Tags : BookingScreen("Tags")
    object Categories : BookingScreen("Categories")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // State to track the currently selected tab
    val screens = listOf(BookingScreen.TaskList, BookingScreen.Completed, BookingScreen.Contacts, BookingScreen.Tags, BookingScreen.Categories)
    var selectedScreen by remember { mutableStateOf<BookingScreen>(BookingScreen.TaskList) }

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
                    label = { Text("Profile") },
                    selected = false,
                    onClick = {
                        // Handle navigation to profile screen
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Sign Out") },
                    selected = false,
                    onClick = {
                        // Handle sign out logic
                        scope.launch { drawerState.close() }
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
                        is BookingScreen.TaskList -> TaskListScreen(navController)
                        is BookingScreen.Completed -> CompletedScreen()
                        is BookingScreen.Contacts -> ContactScreen()
                        is BookingScreen.Tags -> ContactScreen()
                        is BookingScreen.Categories -> ContactScreen()
                    }
                }
            }
        )
    }
}

// Reusable BookingCard, slightly modified for clarity
@Composable
fun BookingCard(
    isTaskCompleted: Boolean,
    onCompleteClick: () -> Unit,
    onReopenClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Box(
//                    modifier = Modifier
//                        .size(90.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(Color(0xFFE0E0E0))
//                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Task Name", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    Text("Task 1", fontSize = 18.sp, color = Color.Black)
                    Text("For", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
                    Text("Jollibee Food Corp.", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Handle cancel click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(text = "Cancel", color = Color.Black, fontWeight = FontWeight.SemiBold)
                }
                TaskButton(
                    isTaskCompleted = isTaskCompleted,
                    onCompleteClick = onCompleteClick,
                    onReopenClick = onReopenClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
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
            if (isTaskCompleted) onReopenClick() else onCompleteClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isTaskCompleted) Color.Black else Color.Green
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
    ) {
        Text(
            text = if (isTaskCompleted) "Re-Open" else "Complete",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ---------------------------------------------------------------------------------------------------



// ---------------------------------------------------------------------------------------------------
@Composable
fun CompletedScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Completed Task List",
            fontSize = 22.sp,
            color = colorResource(id = R.color.green),
            fontWeight = FontWeight.Bold
        )
        // Example with mutable state
        var isTaskCompleted by remember { mutableStateOf(true) }
        BookingCard(
            isTaskCompleted = isTaskCompleted,
            onCompleteClick = { /* No action needed */ },
            onReopenClick = { isTaskCompleted = false }
        )
    }
}

@Preview(showBackground = true, name = "Completed Screen Preview")
@Composable
fun CompletedScreenPreview() {
    CompletedScreen()
}

// ---------------------------------------------------------------------------------------------------
@Composable
fun ContactScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Cancelled Task List",
            fontSize = 22.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
        BookingCard(
            isTaskCompleted = false, // Or some other state for cancelled
            onCompleteClick = { /* No action needed */ },
            onReopenClick = { /* No action needed */ }
        )
    }
}

@Preview(showBackground = true, name = "Cancelled Screen Preview")
@Composable
fun ContactScreenPreview() {
    ContactScreen()
}

// The main preview for the whole HomeScreen
@Preview(showBackground = true)
@Composable
fun MyBookingsScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}