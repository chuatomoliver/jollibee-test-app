package com.certicode.jolibee_test_app.screens.ui_task_add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel
import com.certicode.jolibee_test_app.screens.ui_task_add.TaskUiState
import com.certicode.jolibee_test_app.screens.ui_task_add.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddScreen( navController: NavController, viewModel: TaskViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    var taskNameText by remember { mutableStateOf("") }
    var companyForText by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var statusText by remember { mutableStateOf("Open") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Tasks") }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val newTask = TaskModel(
                        taskName = taskNameText,
                        companyFor = companyForText,
                        status = statusText
                    )
                    viewModel.addTask(newTask)
                    taskNameText = ""
                    companyForText = ""
                    statusText = "Open"
                },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Add Task") },
                text = { Text("Add Task") }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Input Fields
                OutlinedTextField(
                    value = taskNameText,
                    onValueChange = { taskNameText = it },
                    label = { Text("Task Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = companyForText,
                    onValueChange = { companyForText = it },
                    label = { Text("Company For") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = statusText,
                        onValueChange = {},
                        label = { Text("Status") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = isExpanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Open") },
                            onClick = {
                                statusText = "Open"
                                isExpanded = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("Completed") },
                            onClick = {
                                statusText = "Completed"
                                isExpanded = false
                            },
                        )
                    }
                }

                // Task List
                when (uiState) {
                    is TaskUiState.Success -> {
                        val tasks = (uiState as TaskUiState.Success).tasks
                        TaskList(tasks, viewModel)
                    }
                    TaskUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                    is TaskUiState.Error -> {
                        Text(
                            text = "Error: ${(uiState as TaskUiState.Error).message}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    is TaskUiState.Error -> TODO()
                    TaskUiState.Loading -> TODO()
                    is TaskUiState.Success -> TODO()
                }
            }
        }
    }
}

@Composable
fun TaskList(tasks: List<TaskModel>, viewModel: TaskViewModel) {
    LazyColumn {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            TaskItem(task = task, onDelete = {
                viewModel.deleteTask(task = task)
            })
        }
    }
}

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

@Preview(showBackground = true, name = "Cancelled Screen Preview")
@Composable
fun TaskAddScreenPreview() {
    val navController = rememberNavController()
    TaskAddScreen(navController = navController)
}