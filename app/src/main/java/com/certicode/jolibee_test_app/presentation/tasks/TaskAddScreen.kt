package com.certicode.jolibee_test_app.presentation.tasks

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddScreen(navController: NavController, viewModel: TaskViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current // Get the current context

    var taskNameText by remember { mutableStateOf("") }
    var companyForText by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var statusText by remember { mutableStateOf("Open") }

    // Show toast on this screen when a task is successfully added
    LaunchedEffect(uiState) {
        if (uiState is TaskUiState.TaskAdded) {
            Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
            viewModel.resetTaskAddedState() // Reset the state after showing the toast
        }
    }

    // ... rest of your TaskListScreen UI
    // You should use a when statement to handle the different uiState values
    when (uiState) {
        is TaskUiState.Loading -> {
            // Show a loading indicator
        }
        is TaskUiState.Success -> {
            // Show the list of tasks from (uiState as TaskUiState.Success).tasks
        }
        is TaskUiState.Error -> {
            // Show an error message
        }
        is TaskUiState.TaskAdded -> {
            Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Tasks") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
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
            }
        }
    }
}

// TaskItem is a fine standalone composable

@Preview(showBackground = true, name = "Cancelled Screen Preview")
@Composable
fun TaskAddScreenPreview() {
    TaskAddScreen(navController = rememberNavController())
}