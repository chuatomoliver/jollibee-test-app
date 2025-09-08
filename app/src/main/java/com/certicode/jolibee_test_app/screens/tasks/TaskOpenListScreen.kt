package com.certicode.jolibee_test_app.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    listType: String = "open", // Default to "open" for flexibility
    viewModel: TaskViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Use LaunchedEffect to filter the list based on the provided listType
    LaunchedEffect(key1 = listType) {
        viewModel.filterTasks(listType)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("task_add_screen") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = "${listType.replaceFirstChar { it.uppercase() }} Task List",
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            when (uiState) {
                is TaskUiState.Success -> {
                    val tasks = (uiState as TaskUiState.Success).tasks
                    items(
                        items = tasks,
                        key = { task -> task.id }
                    ) { task ->
                        TaskItem(
                            task = task,
                            onDelete = { viewModel.deleteTask(task = task) },
                            onUpdateStatus = { updatedTask ->
                                viewModel.editTask(updatedTask)
                                // Refresh the list after the status is updated
                                viewModel.filterTasks(listType)
                            }
                        )
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
                else -> {}
            }
        }
    }
}

@Composable
fun TaskItem(task: TaskModel, onDelete: () -> Unit, onUpdateStatus: (TaskModel) -> Unit) {
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
            Checkbox(
                checked = task.status == "Completed",
                onCheckedChange = { isChecked ->
                    val newStatus = if (isChecked) "Completed" else "Open"
                    onUpdateStatus(task.copy(status = newStatus))
                }
            )
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
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


@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    val navController = rememberNavController()
    TaskListScreen(navController = navController)
}