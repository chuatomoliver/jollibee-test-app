package com.certicode.jolibee_test_app.screens.ui_task_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel
import com.certicode.jolibee_test_app.screens.BookingCard
import com.certicode.jolibee_test_app.screens.HomeScreen
import com.certicode.jolibee_test_app.screens.ui_task_add.TaskUiState
import com.certicode.jolibee_test_app.screens.ui_task_add.TaskViewModel
import androidx.compose.material3.ExperimentalMaterial3Api // Import this

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController, viewModel: TaskViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("task_add_screen") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        // The main content should be a LazyColumn itself
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header content can be added as a separate item
            item {
                Text(
                    text = "Open Task List",
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            // Task List
            when (uiState) {
                is TaskUiState.Success -> {
                    val tasks = (uiState as TaskUiState.Success).tasks
                    items(
                        items = tasks,
                        key = { task -> task.id }
                    ) { task ->
                        TaskItem(task = task, onDelete = {
                            viewModel.deleteTask(task = task)
                        })
                    }
                }
                TaskUiState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
                is TaskUiState.Error -> {
                    item {
                        Text(
                            text = "Error: ${(uiState as TaskUiState.Error).message}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                else -> {} // Handle other states if necessary
            }
        }
    }
}

// Ensure TaskItem is defined outside the main Composable
@Composable
fun TaskItem(task: TaskModel, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.taskName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Company: ${task.companyFor}",
                    fontSize = 14.sp
                )
                Text(
                    text = "Status: ${task.status}",
                    fontSize = 14.sp
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task"
                )
            }
        }
    }
}

// TaskList is now integrated into the main Composable, so this function is not needed.
// @Composable
// fun TaskList(...) { ... }

@Preview(showBackground = true, name = "Task List Preview")
@Composable
fun TaskListScreenPreview() {
    val navController = rememberNavController()
    // It's better to preview the actual screen you're working on
     TaskListScreen(navController = navController)
}