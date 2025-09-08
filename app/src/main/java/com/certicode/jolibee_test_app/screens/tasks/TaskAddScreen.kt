package com.certicode.jolibee_test_app.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack // Import the back arrow icon
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

    var taskNameText by remember { mutableStateOf("") }
    var companyForText by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var statusText by remember { mutableStateOf("Open") }

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